package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.requests.LoginRequest;
import com.smworks.backendportfolio.models.responses.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authentication")
public class UserAuthenticationController {
    @Autowired
    private IUserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(IUserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/loginUser")
    public GenericResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        return userAuthenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/registerUser")
    public GenericResponse registerUser(@RequestBody LoginRequest loginRequest) {
        return userAuthenticationService.registerUser(loginRequest);
    }

    @PostMapping("/resetPassword")
    public GenericResponse resetPassword(@RequestBody LoginRequest loginRequest) {
        return userAuthenticationService.resetPassword(loginRequest);
    }
}
