package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.requests.LoginRequest;
import com.smworks.backendportfolio.models.responses.LoginResponse;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    private UserRepository userRepository;

    public UserAuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public LoginResponse registerUser(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public LoginResponse resetPassword(LoginRequest loginRequest) {
        return null;
    }
}
