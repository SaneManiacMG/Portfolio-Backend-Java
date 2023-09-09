package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.helpers.mappers.http.UserHttpResponseMapper;
import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
public class UserAuthenticationController {
    @Autowired
    private IUserAuthenticationService userAuthenticationService;

    /*public UserAuthenticationController(IUserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }*/

    @PostMapping("/loginUser")
    public ResponseEntity<Object> authenticateUser(@RequestBody AuthRequest authRequest) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.authenticateUser(authRequest));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody AuthRequest authRequest) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.setPassword(authRequest));
    }

    @PostMapping("/changeAccountStatus/{userId}/to/{status}")
    public ResponseEntity<Object> changeAccountStatus(@PathVariable String userId, @PathVariable AccountStatus status) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.changeAccountStatus(userId, status));
    }

    @PostMapping("/changeAccountRole/{userId}/to/{role}")
    public ResponseEntity<Object> changeAccountRole(@PathVariable String userId, @PathVariable AccountRole role) {
        return UserHttpResponseMapper.mapResponse(userAuthenticationService.changeAccountRole(userId, role));
    }
}
