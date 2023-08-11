package com.smworks.backendportfolio.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    @NonNull
    String username;

    @NonNull
    String password;
}
