package org.miniproject.restuarant.repository;

import org.miniproject.restuarant.model.AppUser;
import org.miniproject.restuarant.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, String> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<AppUser> findByEmailIgnoreCase(String email);

    Page<AppUser> findByRolesContaining(Role managerRole, Pageable pageable);

    Page<AppUser> findByRolesContainingAndEnabledFalse(Role managerRole, Pageable pageable);
}