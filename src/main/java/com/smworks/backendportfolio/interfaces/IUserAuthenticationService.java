package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;

public interface IUserAuthenticationService {
    Object authenticateUser(AuthRequest authRequest);

    Object setPassword(AuthRequest authRequest);

    Object changeAccountStatus(String userId, AccountStatus status);

    Object changeAccountRole(String userId, AccountRole role);
}
