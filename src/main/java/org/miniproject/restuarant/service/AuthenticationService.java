package org.miniproject.restuarant.service;

import org.miniproject.restuarant.dto.authentication.request.AuthenticationRequest;
import org.miniproject.restuarant.dto.authentication.request.RefreshTokenRequest;
import org.miniproject.restuarant.dto.authentication.request.RegistrationRequest;
import org.miniproject.restuarant.dto.authentication.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(final AuthenticationRequest request);

    void register(final RegistrationRequest request);

    AuthenticationResponse refreshToken(final RefreshTokenRequest request);

}
