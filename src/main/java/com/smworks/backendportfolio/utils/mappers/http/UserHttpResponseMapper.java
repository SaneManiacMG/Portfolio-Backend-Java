package com.smworks.backendportfolio.utils.mappers.http;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class UserHttpResponseMapper {
    public static ResponseEntity<Object> mapResponse(Object response) {
        if (response instanceof DataIntegrityViolationException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }

        if (response instanceof Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (response instanceof List) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        if (response == null || response instanceof UsernameNotFoundException) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
