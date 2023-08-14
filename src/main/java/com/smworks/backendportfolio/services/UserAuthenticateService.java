package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.requests.LoginRequest;
import com.smworks.backendportfolio.models.responses.GenericResponse;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticateService implements IUserAuthenticationService {
    @Override
    public GenericResponse authenticateUser(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public GenericResponse registerUser(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public GenericResponse resetPassword(LoginRequest loginRequest) {
        return null;
    }
}
