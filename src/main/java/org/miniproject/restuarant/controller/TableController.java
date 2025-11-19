package org.miniproject.restuarant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.dto.table.TableDTO;
import org.miniproject.restuarant.model.RestaurantTable;
import org.miniproject.restuarant.service.TableService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tables")
@Tag(name = "Tables", description = "Table Management API")
public class TableController {

    private final TableService tableService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public void addTable(
            @Valid
            @RequestBody final TableDTO tableDTO
    ) {
        tableService.addTable(tableDTO);
    }

    @PutMapping("/{tableID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public void updateTable(
            @PathVariable final String tableID,
            @Valid
            @RequestBody final TableDTO tableDTO
    ) {
        tableService.updateTable(tableID, tableDTO);
    }

    @DeleteMapping("/{tableID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public void deleteTable(
            @PathVariable final String tableID
    ) {
        tableService.deleteTable(tableID);
    }

    @GetMapping("/{tableID}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STAFF')")
    public TableDTO getTableById(
            @PathVariable final String tableID
    ) {
        return tableService.getTableById(tableID);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STAFF')")
    public List<TableDTO> getAllTables() {
        return tableService.getAllTables();
    }

    @GetMapping("/available")
    public List<TableDTO> getAvailableTables() {
        return tableService.getAvailableTables();
    }

    @GetMapping("/capacity/{minCapacity}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STAFF')")
    public List<TableDTO> getTablesByCapacity(
            @PathVariable final Integer minCapacity
    ) {
        return tableService.getTablesByCapacity(minCapacity);
    }

    @PatchMapping("/{tableID}/status/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public void updateTableStatus(
            @PathVariable final String tableID,
            @PathVariable final RestaurantTable.TableStatus status
    ) {
        tableService.updateTableStatus(tableID, status);
    }
}
