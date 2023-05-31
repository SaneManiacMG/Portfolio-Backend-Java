package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.filters.JwtRequestFiler;
import com.smworks.backendportfolio.models.Login;
import com.smworks.backendportfolio.models.LoginRequest;
import com.smworks.backendportfolio.models.LoginResponse;
import com.smworks.backendportfolio.models.User;
import com.smworks.backendportfolio.repositories.LoginRepository;
import com.smworks.backendportfolio.repositories.UserRepository;
import com.smworks.backendportfolio.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtRequestFiler jwtRequestFiler;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginRepository loginRepository;

    @Override
    public ResponseEntity<Object> authenticate(LoginRequest request) {
        if (request.getPassword().isBlank() || request.getUserIdentifier().isBlank()) {
            return new ResponseEntity<>("Missing value/s", HttpStatus.NOT_ACCEPTABLE);
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(request.getUserIdentifier());
        Optional<User> existingUserByUsername = userRepository.findByUsername(request.getUserIdentifier());

        String userId;

        if (existingUserByEmail.isPresent()) {
            userId = existingUserByEmail.get().getUserId();
        } else if (existingUserByUsername.isPresent()) {
            userId = existingUserByUsername.get().getUserId();
        } else {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
        }

        Optional<Login> loginUser = loginRepository.findById(userId);

        if (!loginUser.isPresent()) {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
        }

        Login foundUser = loginUser.get();
        String hashedPassword = foundUser.getPassword();
        System.out.println(foundUser.getUsername());
        System.out.println(hashedPassword);

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(foundUser.getUsername(), foundUser.getPassword());
            System.out.println(authenticationToken);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(jwtRequestFiler.getRequest()));

            String accessToken = jwtUtil.generateAccessToken(foundUser);

            if (!foundUser.isEnabled()) {
                return new ResponseEntity<>("Account locked", HttpStatus.FORBIDDEN);
            }

            LoginResponse response = new LoginResponse(foundUser.getUsername(), accessToken);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}