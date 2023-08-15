package com.smworks.backendportfolio.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserResponseMapper {
    public static ResponseEntity<Object> mapResponse(Object response) {
        if (response instanceof Exception) {
            return new ResponseEntity<>(((Exception) response).getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (response == null) {
            return new ResponseEntity<>("User Details not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
