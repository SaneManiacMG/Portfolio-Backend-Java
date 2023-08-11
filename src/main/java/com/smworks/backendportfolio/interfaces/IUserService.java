package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.requests.LoginRequest;
import com.smworks.backendportfolio.models.responses.GenericResponse;

import java.util.Optional;

public interface IUserService {
    public GenericResponse authenticateUser(LoginRequest loginRequest);
    public GenericResponse registerUser(LoginRequest loginRequest);
    public GenericResponse resetPassword(LoginRequest loginRequest);
}
