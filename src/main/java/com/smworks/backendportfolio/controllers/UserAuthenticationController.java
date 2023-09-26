package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.utils.mappers.http.AuthHttpResponseMapper;
import com.smworks.backendportfolio.utils.mappers.http.UserHttpResponseMapper;
import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
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
    public ResponseEntity<Object> resetPassword(@RequestBody AuthRequest authRequest) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.setPassword(authRequest));
    }

    @PutMapping("/changeAccountStatus/{userId}/to/{status}")
    public ResponseEntity<Object> changeAccountStatus(@PathVariable String userId, @PathVariable AccountStatus status) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.changeAccountStatus(userId, status));
    }

    @PutMapping("/changeAccountRole/{userId}/to/{role}")
    public ResponseEntity<Object> changeAccountRole(@PathVariable String userId, @PathVariable AccountRole role) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.changeAccountRole(userId, role));
    }
}
