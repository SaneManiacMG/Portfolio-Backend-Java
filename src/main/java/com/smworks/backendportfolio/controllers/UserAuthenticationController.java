package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import com.smworks.backendportfolio.models.responses.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authentication")
public class UserAuthenticationController {
    @Autowired
    private IUserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(IUserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/loginUser")
    public ResponseEntity<Object> authenticateUser(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(userAuthenticationService.authenticateUser(authRequest), HttpStatus.OK);
    }

    @PostMapping("/registerUser")
    public ResponseEntity<Object> registerUser(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(userAuthenticationService.registerUser(authRequest), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<Object> resetPassword(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(userAuthenticationService.resetPassword(authRequest), HttpStatus.OK);
    }

    @PostMapping("/changeAccountStatus/{userId}/to/{status}")
    public ResponseEntity<Object> changeAccountStatus(@PathVariable String userId, @PathVariable AccountStatus status) {
        return new ResponseEntity<>(userAuthenticationService.changeAccountStatus(userId, status), HttpStatus.OK);
    }

    @PostMapping("/changeAccountRole/{userId}/to/{role}")
    public ResponseEntity<Object> changeAccountRole(@PathVariable String userId, @PathVariable AccountRole role) {
        return new ResponseEntity<>(userAuthenticationService.changeAccountRole(userId, role), HttpStatus.OK);
    }
}
