package org.miniproject.restuarant.service;

import org.miniproject.restuarant.dto.staff.request.CreateStaffRequest;
import org.miniproject.restuarant.dto.staff.request.UpdateStaffRequest;
import org.miniproject.restuarant.dto.staff.response.StaffResponse;
import org.miniproject.restuarant.model.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StaffService {

    // Create a new staff member
    StaffResponse createStaff(CreateStaffRequest request);

    // Get staff by ID
    StaffResponse getStaffById(String staffID);

    // Get staff by user ID
    StaffResponse getStaffByUserId(String userID);

    // Get all staff with pagination
    Page<StaffResponse> getAllStaff(Pageable pageable);

    // Get active/inactive staff
    Page<StaffResponse> getStaffByActiveStatus(boolean isActive, Pageable pageable);

    // Get staff by position
    Page<StaffResponse> getStaffByPosition(String position, Pageable pageable);

    // Get staff by employment type
    Page<StaffResponse> getStaffByEmploymentType(Staff.EmploymentType employmentType, Pageable pageable);

    // Update staff details
    StaffResponse updateStaff(String staffID, UpdateStaffRequest request);

    // Deactivate staff
    void deactivateStaff(String staffID);

    // Reactivate staff
    void reactivateStaff(String staffID);

    // Delete staff (soft delete)
    void deleteStaff(String staffID);
}
