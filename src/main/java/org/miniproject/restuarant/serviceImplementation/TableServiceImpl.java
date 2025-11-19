package org.miniproject.restuarant.serviceImplementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miniproject.restuarant.dto.table.TableDTO;
import org.miniproject.restuarant.exception.BusinessException;
import org.miniproject.restuarant.exception.ErrorCode;
import org.miniproject.restuarant.mapper.TableMapper;
import org.miniproject.restuarant.model.RestaurantTable;
import org.miniproject.restuarant.repository.RestaurantTableRepository;
import org.miniproject.restuarant.service.TableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final RestaurantTableRepository tableRepository;
    private final TableMapper tableMapper;

    @Override
    @Transactional
    public void addTable(TableDTO tableDTO) {
        if (tableRepository.existsByTableNumber(tableDTO.getTableNumber())) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Table with number " + tableDTO.getTableNumber() + " already exists");
        }
        
        RestaurantTable table = tableMapper.toEntity(tableDTO);
        table = tableRepository.save(table);
        tableMapper.toDTO(table);
    }

    @Override
    @Transactional
    public void updateTable(String tableID, TableDTO tableDTO) {
        RestaurantTable existingTable = tableRepository.findByID(tableID);
        
        // Check if the new table number is already taken by another table
        if (!existingTable.getTableNumber().equals(tableDTO.getTableNumber()) && 
            tableRepository.existsByTableNumber(tableDTO.getTableNumber())) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, "Table with number " + tableDTO.getTableNumber() + " already exists");
        }
        
        tableMapper.updateEntityFromDTO(tableDTO, existingTable);
        existingTable = tableRepository.save(existingTable);
        tableMapper.toDTO(existingTable);
    }

    @Override
    @Transactional
    public void deleteTable(String tableID) {
        if (!tableRepository.existsById(tableID)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Table not found with id: " + tableID);
        }
        tableRepository.deleteById(tableID);
    }

    @Override
    @Transactional(readOnly = true)
    public TableDTO getTableById(String tableID) {
        return tableRepository.findById(tableID)
                .map(tableMapper::toDTO)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Table not found with id: " + tableID));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableDTO> getAllTables() {
        return tableRepository.findAll().stream()
                .map(tableMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableDTO> getAvailableTables() {
        return tableRepository.findByStatus(RestaurantTable.TableStatus.AVAILABLE).stream()
                .map(tableMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TableDTO> getTablesByCapacity(Integer minCapacity) {
        return tableRepository.findByCapacityGreaterThanEqual(minCapacity).stream()
                .map(tableMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateTableStatus(String tableID, RestaurantTable.TableStatus status) {
        RestaurantTable table = tableRepository.findById(tableID)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Table not found with id: " + tableID));
        
        table.setStatus(status);
        table = tableRepository.save(table);
        tableMapper.toDTO(table);
    }
}
