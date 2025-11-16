package org.miniproject.restuarant.serviceImplementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miniproject.restuarant.config.security.JwtServices;
import org.miniproject.restuarant.dto.authentication.request.AuthenticationRequest;
import org.miniproject.restuarant.dto.authentication.request.RefreshTokenRequest;
import org.miniproject.restuarant.dto.authentication.request.RegistrationRequest;
import org.miniproject.restuarant.dto.authentication.response.AuthenticationResponse;
import org.miniproject.restuarant.exception.BusinessException;
import org.miniproject.restuarant.exception.ErrorCode;
import org.miniproject.restuarant.mapper.AppUserMapper;
import org.miniproject.restuarant.model.AppUser;
import org.miniproject.restuarant.model.Role;
import org.miniproject.restuarant.repository.AppUserRepository;
import org.miniproject.restuarant.repository.RoleRepository;
import org.miniproject.restuarant.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtServices jwtServices;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        final AppUser user = (AppUser) authentication.getPrincipal();
        final String token = jwtServices.generateAccessToken(user.getUsername());
        final String refreshToken = jwtServices.generateRefreshToken(user.getUsername());
        final String tokenType = "Bearer";
        return AuthenticationResponse
                .builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkEmail(request.getEmail());
        checkPasswords(request.getPassword(), request.getConfirmPassword());

        final Role userRole = roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exists"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final AppUser appUser = appUserMapper.registrationMapper(request);
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setRoles(roles);

        appUserRepository.save(appUser);

        final List<AppUser> appUsers = new ArrayList<>();
        appUsers.add(appUser);
        userRole.setUsers(appUsers);

        roleRepository.save(userRole);

    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        final String newAccessToken = jwtServices.refreshAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();
    }


    private void checkEmail(final String email) {
        final boolean emailExists = appUserRepository.existsByEmailIgnoreCase(email);
        if (emailExists) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS, email);
        }
    }

    private void checkPasswords(String password, String confirmPassword) {
        if (!password.matches(confirmPassword)) {
            throw new BusinessException(ErrorCode.CONFIRM_PASSWORD_MISMATCH, confirmPassword);
        }
    }
}

