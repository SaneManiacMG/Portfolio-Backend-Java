package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.interfaces.IUserDetailsService;
import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.requests.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDetails createUserAccount(@RequestBody UserRequest userDetails) {
        return userDetailsService.createUserAccount(userDetails);
    }

    @GetMapping("/getUser/{userId}")
    public UserDetails getUserDetails(@RequestParam String userId) {
        return userDetailsService.getUserDetails(userId);
    }

    @PutMapping("/updateUser")
    public UserDetails updateUserDetails(@RequestBody UserRequest userDetails) {
        return userDetailsService.updateUserDetails(userDetails);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public String deleteUserDetails(@RequestParam String userId) {
        return userDetailsService.deleteUserDetails(userId);
    }

    @GetMapping
    public List<UserDetails> getAllUserDetails() {
        return userDetailsService.getAllUserDetails();
    }
}
