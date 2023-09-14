package com.smworks.backendportfolio.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer ";

    public AuthResponse(String token) {
        this.token = token;
    }
}
