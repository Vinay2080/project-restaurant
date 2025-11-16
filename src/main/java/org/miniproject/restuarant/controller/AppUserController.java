package org.miniproject.restuarant.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.dto.appUser.request.ChangePasswordRequest;
import org.miniproject.restuarant.dto.appUser.request.ProfileUpdateRequest;
import org.miniproject.restuarant.model.AppUser;
import org.miniproject.restuarant.service.AppUserService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "user", description = "User API")

public class AppUserController {
    private final AppUserService appUserService;

    @PatchMapping("/update/profile")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateProfile(
            @RequestBody
            @Valid final ProfileUpdateRequest request,
            final Authentication authentication) {
        this.appUserService.updateProfileInfo(request, getUserID(authentication));
    }

    @PostMapping("/update/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestBody
            @Valid final ChangePasswordRequest request,
            final Authentication authentication) {
        this.appUserService.changePassword(request, getUserID(authentication));
    }

    @PatchMapping("/deactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateAccount(
            @RequestBody
            @Valid final Authentication authentication) {
        this.appUserService.deactivateAccount(getUserID(authentication));
    }

    @PatchMapping("/reactivate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivateAccount(
            @RequestBody
            @Valid final Authentication authentication) {
        this.appUserService.reactivateAccount(getUserID(authentication));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(Authentication authentication) {
        appUserService.deleteAccount(getUserID(authentication));
    }

    private String getUserID(Authentication authentication) {
        return ((AppUser) authentication.getPrincipal()).getID();
    }
}
