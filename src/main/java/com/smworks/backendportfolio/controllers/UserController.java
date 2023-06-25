package com.smworks.backendportfolio.controllers;

import com.smworks.backendportfolio.models.User;
import com.smworks.backendportfolio.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/getAllUsers")
    public ResponseEntity<Object> findAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/findById")
    public ResponseEntity<Object> findUserById(@RequestBody User user) {
        return userService.findByUserId(user);
    }

    @PostMapping("/findByUsername")
    public ResponseEntity<Object> findByUsername(@RequestBody User user) {
        return userService.findByUsername(user);
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<Object> findUserByEmail(@RequestBody User user) {
        return userService.findByEmail(user);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<Object> updateUser(@RequestBody User user) {
        return userService.updateUserDetails(user);
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestBody User user) {
        return userService.deleteUserRecord(user);
    }

    @PostMapping("/addUser")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        return userService.createUserRecord(user);
    }
}
