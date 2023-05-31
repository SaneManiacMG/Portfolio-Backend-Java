package com.smworks.backendportfolio.models;

import org.springframework.stereotype.Component;

@Component
public class RegisterRequest {
    private String userId;
    private String username;
    private String email;
    private String password;

    public RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public RegisterRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" + "userId='" + userId + '\'' + ", username='" + username + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + '}';
    }
}
