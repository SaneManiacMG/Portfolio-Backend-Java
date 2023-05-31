package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.models.User;
import org.springframework.http.ResponseEntity;


public interface UserService {
    String generateUserId();

    String generateDateTime();

    int generateNumber();

    ResponseEntity<Object> getAllUsers();

    ResponseEntity<Object> findByUserId(User user);

    ResponseEntity<Object> findByUsername(User user);

    ResponseEntity<Object> findByEmail(User user);

    ResponseEntity<Object> updateUserDetails(User user);

    ResponseEntity<Object> deleteUserRecord(User user);

    ResponseEntity<Object> createUserRecord(User user);
}
