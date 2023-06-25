package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.interfaces.UserService;
import com.smworks.backendportfolio.models.User;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private User foundUser;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Object> getAllUsers() {
        try {
            if (!userRepository.findAll().isEmpty())
                return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
            else return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> findByUserId(User user) {
        Optional<User> userOptional = userRepository.findById(user.getUserId());

        try {
            if (userRepository.existsById(user.getUserId())) {
                foundUser = userOptional.get();
                return new ResponseEntity<>(foundUser, HttpStatus.FOUND);
            } else return new ResponseEntity<>("User ID not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> findByUsername(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());

        try {
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                foundUser = userOptional.get();
                return new ResponseEntity<>(foundUser, HttpStatus.FOUND);
            } else return new ResponseEntity<>("Username not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> findByEmail(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        try {
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                foundUser = userOptional.get();
                return new ResponseEntity<>(foundUser, HttpStatus.FOUND);
            } else return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> updateUserDetails(User user) {
        Optional<User> existingUserByUsername = userRepository.findByUsername(user.getUsername());
        Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> existingUserById = userRepository.findById(user.getUserId());

        if (existingUserById.isPresent()) {
            if (existingUserByEmail.isEmpty() && existingUserByUsername.isEmpty()) {
                try {
                    userRepository.save(user);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("Username/email already taken", HttpStatus.CONFLICT);
            }
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Object> deleteUserRecord(User user) {
        try {
            if (userRepository.existsById(user.getUserId())) {
                userRepository.deleteById(user.getUserId());
                return new ResponseEntity<>("User deleted", HttpStatus.OK);
            } else return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> createUserRecord(User user) {
        Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> existingUserByUsername = userRepository.findByUsername(user.getUsername());

        if (existingUserByEmail.isPresent() || existingUserByUsername.isPresent()) {
            return new ResponseEntity<>("Username/email already taken", HttpStatus.CONFLICT);
        } else {
            try {
                user.setUserId(generateUserId());
                User savedUser = userRepository.save(user);
                return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>("An error has occurred while saving the user \n" + e, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    public String generateUserId() {
        String generatedSequence = generateDateTime() + generateNumber();
        return generatedSequence;
    }

    @Override
    public String generateDateTime() {
        DateTimeFormatter userIdDateTimeFormat = DateTimeFormatter.ofPattern("ssmmHHddMM");
        LocalDateTime now = LocalDateTime.now();
        return userIdDateTimeFormat.format(now);
    }

    @Override
    public int generateNumber() {
        Random random = new Random();
        int upperLimit = 99;
        int lowerLimit = 10;
        return random.nextInt(upperLimit - lowerLimit) + lowerLimit;
    }
}
