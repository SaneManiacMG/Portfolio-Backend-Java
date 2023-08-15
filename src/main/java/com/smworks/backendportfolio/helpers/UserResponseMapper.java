package com.smworks.backendportfolio.helpers;

import com.smworks.backendportfolio.models.entities.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserResponseMapper {
    public static ResponseEntity<Object> mapResponse(Object response) {
        if (response instanceof Exception) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
