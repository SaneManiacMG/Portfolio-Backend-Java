package com.smworks.backendportfolio.models;

public class LoginRequest {
    private String userIdentifier;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String userIdentifier, String password) {
        this.userIdentifier = userIdentifier;
        this.password = password;
    }

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userIdentifier='" + userIdentifier + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
