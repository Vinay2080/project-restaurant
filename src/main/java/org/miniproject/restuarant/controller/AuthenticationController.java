package org.miniproject.restuarant.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.dto.authentication.request.AuthenticationRequest;
import org.miniproject.restuarant.dto.authentication.request.RefreshTokenRequest;
import org.miniproject.restuarant.dto.authentication.request.RegistrationRequest;
import org.miniproject.restuarant.dto.authentication.response.AuthenticationResponse;
import org.miniproject.restuarant.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication API")

public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(
            @Valid
            @RequestBody final AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> register(
            @Valid
            @RequestBody final RegistrationRequest request
    ) {
        authenticationService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PostMapping("/refresh/token")
    ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody
            RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

}
