package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.helpers.UserResponseMapper;
import com.smworks.backendportfolio.interfaces.IUserDetailsService;
import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.models.responses.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return UserResponseMapper.mapResponse(userDetailsService.createUserAccount(userDetails));
    }

    @GetMapping("/getUser/{userId}")
    public ResponseEntity<Object> getUserDetails(@PathVariable String userId) {
        return UserResponseMapper.mapResponse(userDetailsService.getUserDetails(userId));
    }

    @PutMapping("/updateUser")
    public UserDetails updateUserDetails(@RequestBody UserRequest userDetails) {
        return userDetailsService.updateUserDetails(userDetails);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public String deleteUserDetails(@PathVariable String userId) {
        return userDetailsService.deleteUserDetails(userId);
    }

    @GetMapping
    public List<UserDetails> getAllUserDetails() {
        return userDetailsService.getAllUserDetails();
    }
}
