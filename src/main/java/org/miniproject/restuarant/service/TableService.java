package org.miniproject.restuarant.service;

import org.miniproject.restuarant.dto.table.TableDTO;
import org.miniproject.restuarant.model.RestaurantTable;

import java.util.List;

public interface TableService {
    
    void addTable(TableDTO tableDTO);
    
    void updateTable(String tableID, TableDTO tableDTO);
    
    void deleteTable(String tableID);
    
    TableDTO getTableById(String tableID);
    
    List<TableDTO> getAllTables();
    
    List<TableDTO> getAvailableTables();
    
    List<TableDTO> getTablesByCapacity(Integer minCapacity);
    
    void updateTableStatus(String tableID, RestaurantTable.TableStatus status);
}
