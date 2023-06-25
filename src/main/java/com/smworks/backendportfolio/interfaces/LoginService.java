package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<Object> authenticate(LoginRequest request);
}
