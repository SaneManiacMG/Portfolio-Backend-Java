package com.smworks.backendportfolio.utils.mappers.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

public class AuthHttpResponseMapper {
    public static ResponseEntity<Object> mapResponse(Object response) {
        if (response instanceof AuthenticationException || response == null) {
            return new ResponseEntity<>("Invalid Username/Emails or Password", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
