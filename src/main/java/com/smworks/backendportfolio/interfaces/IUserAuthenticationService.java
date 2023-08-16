package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import com.smworks.backendportfolio.models.responses.AuthResponse;

public interface IUserAuthenticationService {
    Object authenticateUser(AuthRequest authRequest);
    Object registerUser(AuthRequest authRequest);
    Object resetPassword(AuthRequest authRequest);
    Object changeAccountStatus(String userId, AccountStatus status);
    Object changeAccountRole(String userId, AccountRole role);
}
