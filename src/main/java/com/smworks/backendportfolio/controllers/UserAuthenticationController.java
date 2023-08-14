package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.requests.LoginRequest;
import com.smworks.backendportfolio.models.responses.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user-authentication")
public class UserAuthenticationController {
    @Autowired
    private IUserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(IUserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/authenticate-user")
    public GenericResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return userAuthenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/register-user")
    public GenericResponse registerUser(@RequestBody LoginRequest loginRequest) {
        return userAuthenticationService.registerUser(loginRequest);
    }

    @PostMapping("/reset-password")
    public GenericResponse resetPassword(@RequestBody LoginRequest loginRequest) {
        return userAuthenticationService.resetPassword(loginRequest);
    }
}
