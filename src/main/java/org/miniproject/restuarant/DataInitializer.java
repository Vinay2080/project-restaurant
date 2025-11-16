package org.miniproject.restuarant;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.model.AppUser;
import org.miniproject.restuarant.model.Role;
import org.miniproject.restuarant.repository.AppUserRepository;
import org.miniproject.restuarant.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        // Create roles if they don't exist
        createRoleIfNotFound("ROLE_USER");
        createRoleIfNotFound("ROLE_STAFF");
        createRoleIfNotFound("ROLE_MANAGER");
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");

        // Create admin if not exists
        if (!appUserRepository.existsByEmailIgnoreCase("admin@restaurant.com")) {
            // Initialize the user in the admin role if it's null
            if (adminRole.getUsers() == null) {
                adminRole.setUsers(new ArrayList<>());
            }

            // Create and save the admin user
            AppUser admin = new AppUser();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@restaurant.com");
            admin.setPassword(passwordEncoder.encode("Admin_123!"));
            admin.setRoles(List.of(adminRole));
            admin.setEnabled(true);
            admin.setEmailVerified(true);
            admin.setPhoneVerified(true);

            // Save the admin user first
            AppUser savedAdmin = appUserRepository.save(admin);

            // Add the admin to each role's user set and save the roles
            adminRole.getUsers().add(savedAdmin);

            // Save all roles
            roleRepository.save(adminRole);
        }
    }

    @Transactional
    protected Role createRoleIfNotFound(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(name);
                    role.setCreatedBy("ADMIN");
                    return roleRepository.save(role);
                });
    }
}
