package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.models.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface RegisterService {
    ResponseEntity<Object> createNewUserLogin(RegisterRequest registerRequest);
}
