package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.models.RegisterRequest;
import com.smworks.backendportfolio.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/newUser")
    public ResponseEntity<Object> registerNewUser(@RequestBody RegisterRequest registerRequest) {
        return registerService.createNewUserLogin(registerRequest);
    }
}
