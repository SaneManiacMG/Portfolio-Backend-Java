package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.requests.LoginRequest;
import com.smworks.backendportfolio.models.responses.LoginResponse;

public interface IUserAuthenticationService {
    LoginResponse authenticateUser(LoginRequest loginRequest);
    LoginResponse registerUser(LoginRequest loginRequest);
    LoginResponse resetPassword(LoginRequest loginRequest);
}
