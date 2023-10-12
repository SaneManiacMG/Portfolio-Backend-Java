package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import com.smworks.backendportfolio.models.requests.PasswordResetRequest;

public interface IUserAuthenticationService {
    Object authenticateUser(AuthRequest authRequest);

    Object setPassword(PasswordResetRequest passwordResetRequest);

    Object changeAccountStatus(String userId, AccountStatus status);

    Object removeAccountRole(String userId, AccountRole role);

    Object addAccountRole(String userId, AccountRole role);
}
