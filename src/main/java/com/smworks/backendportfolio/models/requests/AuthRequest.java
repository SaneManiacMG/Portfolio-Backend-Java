package com.smworks.backendportfolio.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequest {
    @NonNull
    String userId;

    @NonNull
    String password;
}
