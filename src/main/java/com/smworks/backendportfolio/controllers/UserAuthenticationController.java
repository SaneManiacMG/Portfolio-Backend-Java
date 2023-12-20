package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.utils.mappers.http.AuthHttpResponseMapper;
import com.smworks.backendportfolio.utils.mappers.http.UserHttpResponseMapper;
import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import com.smworks.backendportfolio.models.requests.PasswordResetRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class UserAuthenticationController {
    private IUserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(IUserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Object> authenticateUser(@RequestBody AuthRequest authRequest) {
        return AuthHttpResponseMapper.mapResponse(userAuthenticationService.authenticateUser(authRequest));
    }

    @PutMapping("/setPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.setPassword(passwordResetRequest));
    }

    @PutMapping("/changeAccountStatus/{userId}/to/{status}")
    public ResponseEntity<Object> changeAccountStatus(@PathVariable String userId, @PathVariable AccountStatus status) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.changeAccountStatus(userId, status));
    }

    @PutMapping("/removeAccountRole/{role}/from/{userId}")
    public ResponseEntity<Object> removeAccountRole(@PathVariable String userId, @PathVariable AccountRole role) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.removeAccountRole(userId, role));
    }

    @PutMapping("/addAccountRole/{role}/to/{userId}")
    public ResponseEntity<Object> addAccountRole(@PathVariable String userId, @PathVariable AccountRole role) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.addAccountRole(userId, role));
    }
}
