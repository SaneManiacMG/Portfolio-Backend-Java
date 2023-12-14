package com.smworks.backendportfolio.models.requests;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    @NotNull
    private String userId;
    private String currentPassword;
    private String newPassword;
}
