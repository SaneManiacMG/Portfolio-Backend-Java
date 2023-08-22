package com.smworks.backendportfolio.helpers.mappers.http;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserHttpResponseMapper {
    public static ResponseEntity<Object> mapResponse(Object response) {
        if (response instanceof DataIntegrityViolationException) {
            return new ResponseEntity<>(((DataIntegrityViolationException) response).getMessage(), HttpStatus.CONFLICT);
        }

        if (response instanceof Exception) {
            return new ResponseEntity<>(((Exception) response).getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (response instanceof List) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        if (response == null) {
            return new ResponseEntity<>("User details not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
