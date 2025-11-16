package org.miniproject.restuarant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.dto.admin.request.CreateManagerRequest;
import org.miniproject.restuarant.dto.admin.request.UpdateManagerRequest;
import org.miniproject.restuarant.dto.admin.response.ManagerResponse;
import org.miniproject.restuarant.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Admin API for managing managers")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/managers")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new manager")
    public ManagerResponse createManager(@RequestBody @Valid CreateManagerRequest request) {
        return adminService.createManager(request);
    }

    @GetMapping("/managers/{managerID}")
    @Operation(summary = "Get manager by ID")
    public ManagerResponse getManagerById(@PathVariable String managerID) {
        return adminService.getManagerByID(managerID);
    }

    @GetMapping("/managers")
    @Operation(summary = "Get all managers with pagination")
    public Page<ManagerResponse> getAllManagers(
            @PageableDefault(size = 10, sort = "createdDate") Pageable pageable) {
        return adminService.getAllManagers(pageable);
    }

    @PutMapping("/managers/{managerID}")
    @Operation(summary = "Update manager details")
    public ManagerResponse updateManager(
            @PathVariable String managerID,
            @RequestBody @Valid UpdateManagerRequest request) {
        return adminService.updateManager(managerID, request);
    }

    @PatchMapping("/managers/{managerID}/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deactivate a manager account")
    public void deactivateManager(@PathVariable String managerID) {
        adminService.deactivateManager(managerID);
    }

    @PatchMapping("/managers/{managerID}/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Reactivate a manager account")
    public void reactivateManager(@PathVariable String managerID) {
        adminService.reactivateManager(managerID);
    }

    @DeleteMapping("/managers/{managerID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a manager account")
    public void deleteManager(@PathVariable String managerID) {
        adminService.deleteManager(managerID);
    }

    @GetMapping("/managers/deactivated")
    @Operation(summary = "Get all deactivated managers")
    public Page<ManagerResponse> getDeactivatedManagers(
            @PageableDefault(size = 10) Pageable pageable) {
        return adminService.getDeactivatedManagers(pageable);
    }
}