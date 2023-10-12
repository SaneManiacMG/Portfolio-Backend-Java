package com.smworks.backendportfolio.models.requests;

import io.micrometer.core.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    @NonNull
    private String userId;
    private String currentPassword;
    private String newPassword;
}
