package com.smworks.backendportfolio.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {
    String username;
    String email;
    String firstName;
    String lastName;
    String phoneNumber;
}
