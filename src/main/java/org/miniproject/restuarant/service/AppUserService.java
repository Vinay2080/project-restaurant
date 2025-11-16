package org.miniproject.restuarant.service;

import org.miniproject.restuarant.dto.appUser.request.ChangePasswordRequest;
import org.miniproject.restuarant.dto.appUser.request.ProfileUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
    void updateProfileInfo(ProfileUpdateRequest request, String userID);

    void changePassword(ChangePasswordRequest request, String userID);

    void deactivateAccount(String userID);

    void reactivateAccount(String userID);

    void deleteAccount(String userID);
}
