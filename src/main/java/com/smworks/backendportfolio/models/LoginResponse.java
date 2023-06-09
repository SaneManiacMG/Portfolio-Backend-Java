package com.smworks.backendportfolio.models;

public class LoginResponse {
    private String userId;
    private String accessToken;

    public LoginResponse(String userId, String accessToken) {
        this.userId = userId;
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
