package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.requests.LoginRequest;
import com.smworks.backendportfolio.models.responses.GenericResponse;

public interface IUserAuthenticationService {
    GenericResponse authenticateUser(LoginRequest loginRequest);
    GenericResponse registerUser(LoginRequest loginRequest);
    GenericResponse resetPassword(LoginRequest loginRequest);
}
