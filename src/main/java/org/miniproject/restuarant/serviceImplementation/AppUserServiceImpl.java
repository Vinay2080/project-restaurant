package org.miniproject.restuarant.serviceImplementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.miniproject.restuarant.dto.appUser.request.ChangePasswordRequest;
import org.miniproject.restuarant.dto.appUser.request.ProfileUpdateRequest;
import org.miniproject.restuarant.exception.BusinessException;
import org.miniproject.restuarant.exception.ErrorCode;
import org.miniproject.restuarant.mapper.AppUserMapper;
import org.miniproject.restuarant.model.AppUser;
import org.miniproject.restuarant.repository.AppUserRepository;
import org.miniproject.restuarant.service.AppUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor

public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return appUserRepository.findByEmailIgnoreCase(userEmail).orElseThrow(() -> new UsernameNotFoundException("user with provided username: " + userEmail));
    }

    @Override
    @Transactional
    public void updateProfileInfo(ProfileUpdateRequest request, String userID) {
        final AppUser savedUser = appUserRepository.findById(userID)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userID));

        // Update only non-null fields from the request to the existing user
        appUserMapper.profileUpdateMapper(request, savedUser);

        // Save the updated user
        appUserRepository.save(savedUser);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request, String userID) {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException(ErrorCode.CONFIRM_PASSWORD_MISMATCH, request.getConfirmPassword());
        }
        final AppUser appUser = appUserRepository.findById(userID)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userID));
        if (!passwordEncoder.matches(request.getPassword(), appUser.getPassword())) {
            throw new BusinessException(ErrorCode.CURRENT_PASSWORD_MISMATCH);
        }

        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        appUser.setPassword(encodedPassword);


    }

    @Override
    @Transactional
    public void deactivateAccount(String userID) {
        AppUser appUser = this.appUserRepository.findById(userID).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!appUser.isEnabled()) {
            throw new BusinessException(ErrorCode.NO_STATUS_CHANGE);
        }
        appUser.setEnabled(false);

    }

    @Override
    public void reactivateAccount(String userID) {
        AppUser appUser = this.appUserRepository.findById(userID).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (appUser.isEnabled()) {
            throw new BusinessException(ErrorCode.NO_STATUS_CHANGE);
        }
        appUser.setEnabled(true);
    }

    @Override
    public void deleteAccount(String userID) {
        AppUser appUser = appUserRepository.findById(userID).orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));
        appUserRepository.delete(appUser);
    }

}
