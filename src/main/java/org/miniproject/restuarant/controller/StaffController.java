package org.miniproject.restuarant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.config.openAPI.StaffApiDocumentation.*;
import org.miniproject.restuarant.dto.staff.request.CreateStaffRequest;
import org.miniproject.restuarant.dto.staff.request.UpdateStaffRequest;
import org.miniproject.restuarant.dto.staff.response.StaffResponse;
import org.miniproject.restuarant.model.Staff;
import org.miniproject.restuarant.service.StaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class StaffController {

    private final StaffService staffService;

    @PostMapping
    @ResponseStatus
    @CreateStaffApiDoc
    @PreAuthorize("hasRole('ADMIN')")
    public StaffResponse createStaff(
            @CreateStaffRequestBody
            @RequestBody @Valid CreateStaffRequest request) {
        return staffService.createStaff(request);
    }

    @GetMapping("/{staffId}")
    @GetStaffByIdApiDoc
    public StaffResponse getStaffById(
            @StaffIdParam @PathVariable String staffId) {
        return staffService.getStaffById(staffId);
    }

    @GetMapping("/user/{userId}")
    @GetStaffByUserIdApiDoc
    public StaffResponse getStaffByUserId(
            @UserIdParam @PathVariable String userId) {
        return staffService.getStaffByUserId(userId);
    }

    @GetMapping
    @GetAllStaffApiDoc
    public Page<StaffResponse> getAllStaff(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return staffService.getAllStaff(pageable);
    }

    @GetMapping("/active")
    @GetStaffByActiveStatusApiDoc
    public Page<StaffResponse> getStaffByActiveStatus(
            @ActiveStatusParam @RequestParam(defaultValue = "true") boolean active,
            @PageableDefault(size = 20) Pageable pageable) {
        return staffService.getStaffByActiveStatus(active, pageable);
    }

    @GetMapping("/position/{position}")
    @GetStaffByPositionApiDoc
    public Page<StaffResponse> getStaffByPosition(
            @PositionParam @PathVariable String position,
            @PageableDefault(size = 20) Pageable pageable) {
        return staffService.getStaffByPosition(position, pageable);
    }

    @GetMapping("/employment-type/{employmentType}")
    @GetStaffByEmploymentTypeApiDoc
    public Page<StaffResponse> getStaffByEmploymentType(
            @EmploymentTypeParam @PathVariable Staff.EmploymentType employmentType,
            @PageableDefault(size = 20) Pageable pageable) {
        return staffService.getStaffByEmploymentType(employmentType, pageable);
    }

    @PutMapping("/{staffId}")
    @UpdateStaffApiDoc
    public StaffResponse updateStaff(
            @StaffIdParam @PathVariable String staffId,
            @UpdateStaffRequestBody
            @RequestBody @Valid UpdateStaffRequest request) {
        return staffService.updateStaff(staffId, request);
    }

    @PatchMapping("/{staffId}/deactivate")
    @DeactivateStaffApiDoc
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivateStaff(
            @StaffIdParam @PathVariable String staffId) {
        staffService.deactivateStaff(staffId);
    }

    @PatchMapping("/{staffId}/reactivate")
    @ReactivateStaffApiDoc
    @PreAuthorize("hasRole('ADMIN')")
    public void reactivateStaff(
            @StaffIdParam @PathVariable String staffId) {
        staffService.reactivateStaff(staffId);
    }

    @DeleteMapping("/{staffId}")
    @DeleteStaffApiDoc
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStaff(
            @StaffIdParam @PathVariable String staffId) {
        staffService.deleteStaff(staffId);
    }
}
