package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.helpers.mappers.http.UserHttpResponseMapper;
import com.smworks.backendportfolio.interfaces.IUserDetailsService;
import com.smworks.backendportfolio.models.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserDetailsController {
    @Autowired
    private IUserDetailsService userDetailsService;

    public UserDetailsController(IUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> createUserAccount(@RequestBody UserRequest userDetails) {
        return UserHttpResponseMapper.mapResponse(userDetailsService.createUserAccount(userDetails));
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<Object> getUserDetails(@PathVariable String userId) {
        return UserHttpResponseMapper.mapResponse(userDetailsService.getUserDetails(userId));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUserDetails(@RequestBody UserRequest userDetails) {
        return UserHttpResponseMapper.mapResponse(userDetailsService.updateUserDetails(userDetails));
    }

    @DeleteMapping("/deleteUser/{userId}")
    public Object deleteUserDetails(@PathVariable String userId) {
        return UserHttpResponseMapper.mapResponse(userDetailsService.deleteUserDetails(userId));
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserDetails() {
        return new ResponseEntity<>(userDetailsService.getAllUserDetails(), HttpStatus.OK);
    }
}
