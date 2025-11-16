package org.miniproject.restuarant.serviceImplementation;

import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.dto.admin.request.CreateManagerRequest;
import org.miniproject.restuarant.dto.admin.request.UpdateManagerRequest;
import org.miniproject.restuarant.dto.admin.response.ManagerResponse;
import org.miniproject.restuarant.exception.BusinessException;
import org.miniproject.restuarant.exception.ErrorCode;
import org.miniproject.restuarant.mapper.ManagerMapper;
import org.miniproject.restuarant.model.AppUser;
import org.miniproject.restuarant.model.Role;
import org.miniproject.restuarant.repository.AppUserRepository;
import org.miniproject.restuarant.repository.RoleRepository;
import org.miniproject.restuarant.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class AdminServiceImplementation implements AdminService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ManagerMapper managerMapper;

    @Override
    @Transactional
    public ManagerResponse createManager(CreateManagerRequest request) {
        // Check if email already exists
        if (appUserRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS, request.getEmail());
        }

        // Find or create a MANAGER role
        Role managerRole = roleRepository.findByName("ROLE_MANAGER")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ROLE_MANAGER");
                    return roleRepository.save(role);
                });

        // Create a new manager user
        AppUser manager = managerMapper.createManagerMapper(request);
        manager.setPassword(passwordEncoder.encode(request.getPassword()));
        manager.setRoles(Collections.singletonList(managerRole));

        AppUser savedManager = appUserRepository.save(manager);
        return managerMapper.toManagerResponse(savedManager);
    }

    @Override
    @Transactional(readOnly = true)
    public ManagerResponse getManagerByID(String managerID) {
        AppUser manager = findManagerByID(managerID);
        return managerMapper.toManagerResponse(manager);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManagerResponse> getAllManagers(Pageable pageable) {
        Role managerRole = roleRepository.findByName("ROLE_MANAGER")
                .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND, "ROLE_MANAGER"));

        return appUserRepository.findByRolesContaining(managerRole, pageable)
                .map(managerMapper::toManagerResponse);
    }

    @Override
    @Transactional
    public ManagerResponse updateManager(String managerID, UpdateManagerRequest request) {
        AppUser manager = findManagerByID(managerID);

        managerMapper.updateManagerMapper(request, manager);
        AppUser updatedManager = appUserRepository.save(manager);
        return managerMapper.toManagerResponse(updatedManager);
    }

    @Override
    @Transactional
    public void deactivateManager(String managerID) {
        AppUser manager = findManagerByID(managerID);
        if (!manager.isEnabled()) {
            throw new BusinessException(ErrorCode.USER_ALREADY_DEACTIVATED, managerID);
        }
        manager.setEnabled(false);
        appUserRepository.save(manager);
    }

    @Override
    @Transactional
    public void reactivateManager(String managerID) {
        AppUser manager = findManagerByID(managerID);
        if (manager.isEnabled()) {
            throw new BusinessException(ErrorCode.USER_ALREADY_ACTIVE, managerID);
        }
        manager.setEnabled(true);
        appUserRepository.save(manager);
    }

    @Override
    @Transactional
    public void deleteManager(String managerID) {
        AppUser manager = findManagerByID(managerID);

        appUserRepository.delete(manager);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManagerResponse> getDeactivatedManagers(Pageable pageable) {
        Role managerRole = roleRepository.findByName("ROLE_MANAGER")
                .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND, "ROLE_MANAGER"));

        return appUserRepository.findByRolesContainingAndEnabledFalse(managerRole, pageable)
                .map(managerMapper::toManagerResponse);
    }

    private AppUser findManagerByID(String managerID) {
        return appUserRepository.findById(managerID)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, managerID));
    }
}