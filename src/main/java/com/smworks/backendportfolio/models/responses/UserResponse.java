package com.smworks.backendportfolio.models.responses;

import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    String username;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
    LocalDateTime dateCreated;
    LocalDateTime dateModified;
    AccountRole accountRole;
    AccountStatus accountStatus;
}
