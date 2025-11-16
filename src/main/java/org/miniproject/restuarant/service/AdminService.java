package org.miniproject.restuarant.service;

import org.miniproject.restuarant.dto.admin.request.CreateManagerRequest;
import org.miniproject.restuarant.dto.admin.request.UpdateManagerRequest;
import org.miniproject.restuarant.dto.admin.response.ManagerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    // Create a new manager
    ManagerResponse createManager(CreateManagerRequest request);

    // Get manager by ID
    ManagerResponse getManagerByID(String managerID);

    // Get all managers with pagination
    Page<ManagerResponse> getAllManagers(Pageable pageable);

    // Update manager details
    ManagerResponse updateManager(String managerID, UpdateManagerRequest request);

    // Deactivate the manager account
    void deactivateManager(String managerID);

    // Reactivate the manager account
    void reactivateManager(String managerID);

    // Delete a manager account
    void deleteManager(String managerID);

    // Get all deactivated managers
    Page<ManagerResponse> getDeactivatedManagers(Pageable pageable);
}