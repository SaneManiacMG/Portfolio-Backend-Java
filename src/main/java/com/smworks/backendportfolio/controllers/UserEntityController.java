package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.helpers.mappers.http.UserHttpResponseMapper;
import com.smworks.backendportfolio.interfaces.IUserEntityService;
import com.smworks.backendportfolio.models.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserEntityController {
    @Autowired
    private IUserEntityService userDetailsService;

    public UserEntityController(IUserEntityService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUserAccount(@RequestBody UserRequest userDetails) {
        return UserHttpResponseMapper.mapResponse(userDetailsService.createUserEntity(userDetails));
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<Object> getUserDetails(@PathVariable String userId) {
        return UserHttpResponseMapper.mapResponse(userDetailsService.getUserEntityResponse(userId));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUserDetails(@RequestBody UserRequest userDetails) {
        return UserHttpResponseMapper.mapResponse(userDetailsService.updateUserEntity(userDetails));
    }

    @DeleteMapping("/deleteUser/{userId}")
    public Object deleteUserDetails(@PathVariable String userId) {
        return UserHttpResponseMapper.mapResponse(userDetailsService.deleteUserEntity(userId));
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserDetails() {
        return new ResponseEntity<>(userDetailsService.getAllUserEntities(), HttpStatus.OK);
    }
}
