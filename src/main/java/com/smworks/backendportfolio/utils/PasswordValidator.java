package com.smworks.backendportfolio.utils;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator {
    public static List<String> validatePassword(String password) {
        List<String> validationErrors = new ArrayList<>();
        if (password.length() < 8) {
            validationErrors.add("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*")) {
            validationErrors.add("Password must contain at least one uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            validationErrors.add("Password must contain at least one lowercase letter");
        }
        if (!password.matches(".*[0-9].*")) {
            validationErrors.add("Password must contain at least one number");
        }
        if (!password.matches(".*[!@#$%^&*].*")) {
            validationErrors.add("Password must contain at least one special character");
        }
        if (password.matches(".*\\s.*")) {
            validationErrors.add("Password must not contain any whitespace");
        }
        return validationErrors;
    }
}
