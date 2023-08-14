package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.requests.LoginRequest;
import com.smworks.backendportfolio.models.responses.GenericResponse;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    private UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
