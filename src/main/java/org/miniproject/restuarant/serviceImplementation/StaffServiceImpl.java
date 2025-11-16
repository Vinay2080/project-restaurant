package org.miniproject.restuarant.serviceImplementation;

import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.dto.staff.request.CreateStaffRequest;
import org.miniproject.restuarant.dto.staff.request.UpdateStaffRequest;
import org.miniproject.restuarant.dto.staff.response.StaffResponse;
import org.miniproject.restuarant.exception.BusinessException;
import org.miniproject.restuarant.exception.ErrorCode;
import org.miniproject.restuarant.mapper.StaffMapper;
import org.miniproject.restuarant.model.AppUser;
import org.miniproject.restuarant.model.Staff;
import org.miniproject.restuarant.repository.AppUserRepository;
import org.miniproject.restuarant.repository.StaffRepository;
import org.miniproject.restuarant.service.StaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final AppUserRepository appUserRepository;
    private final StaffMapper staffMapper;

    @Override
    @Transactional
    public StaffResponse createStaff(CreateStaffRequest request) {
        // Check if employee ID already exists
        if (staffRepository.existsByID(request.getEmployeeID())) {
            throw new BusinessException(ErrorCode.RESOURCE_ALREADY_EXISTS, request.getEmployeeID());
        }

        // Find the user
        AppUser user = appUserRepository.findById(request.getUserID())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, request.getUserID()));

        // Map request to entity
        Staff staff = staffMapper.toEntity(request);
        staff.setUser(user);

        // Set salary based on an employment type
        if (staff.getEmploymentType() == Staff.EmploymentType.FULL_TIME ||
                staff.getEmploymentType() == Staff.EmploymentType.PART_TIME) {
            if (staff.getMonthlySalary() == null) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR,
                        "Monthly salary is required for full-time and part-time employees");
            }
        } else {
            if (staff.getHourlyRate() == null) {
                throw new BusinessException(ErrorCode.VALIDATION_ERROR,
                        "Hourly rate is required for contract and temporary employees");
            }
        }

        // Save the staff
        Staff savedStaff = staffRepository.save(staff);
        return staffMapper.toResponse(savedStaff);
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponse getStaffById(String staffID) {
        Staff staff = findStaffById(staffID);
        return staffMapper.toResponse(staff);
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponse getStaffByUserId(String userID) {
        Staff staff = staffRepository.findByUserId(userID)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "No staff found for user ID: " + userID));
        return staffMapper.toResponse(staff);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StaffResponse> getAllStaff(Pageable pageable) {
        return staffRepository.findAll(pageable)
                .map(staffMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StaffResponse> getStaffByActiveStatus(boolean isActive, Pageable pageable) {
        return staffRepository.findByActiveStatus(isActive, pageable)
                .map(staffMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StaffResponse> getStaffByPosition(String position, Pageable pageable) {
        return staffRepository.findByPosition(position, pageable)
                .map(staffMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StaffResponse> getStaffByEmploymentType(Staff.EmploymentType employmentType, Pageable pageable) {
        return staffRepository.findByEmploymentType(employmentType, pageable)
                .map(staffMapper::toResponse);
    }

    @Override
    @Transactional
    public StaffResponse updateStaff(String staffID, UpdateStaffRequest request) {
        Staff staff = findStaffById(staffID);

        // Update fields if they are not null in the request
        if (request.getPosition() != null) {
            staff.setPosition(request.getPosition());
        }

        if (request.getEmploymentType() != null) {
            staff.setEmploymentType(request.getEmploymentType());
        }

        if (request.getHourlyRate() != null) {
            staff.setHourlyRate(request.getHourlyRate());
        }

        if (request.getMonthlySalary() != null) {
            staff.setMonthlySalary(request.getMonthlySalary());
        }

        if (request.getIsActive() != null) {
            staff.setActive(request.getIsActive());
        }

        Staff updatedStaff = staffRepository.save(staff);
        return staffMapper.toResponse(updatedStaff);
    }

    @Override
    @Transactional
    public void deactivateStaff(String staffID) {
        Staff staff = findStaffById(staffID);
        if (!staff.isActive()) {
            throw new BusinessException(ErrorCode.USER_ALREADY_DEACTIVATED, staffID);
        }
        staff.setActive(false);
        staffRepository.save(staff);
    }

    @Override
    @Transactional
    public void reactivateStaff(String staffID) {
        Staff staff = findStaffById(staffID);
        if (staff.isActive()) {
            throw new BusinessException(ErrorCode.USER_ALREADY_ACTIVE, staffID);
        }
        staff.setActive(true);
        staffRepository.save(staff);
    }

    @Override
    @Transactional
    public void deleteStaff(String staffID) {
        Staff staff = findStaffById(staffID);
        staffRepository.delete(staff);
    }

    private Staff findStaffById(String staffID) {
        return staffRepository.findById(staffID)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Staff not found with ID: " + staffID));
    }
}
