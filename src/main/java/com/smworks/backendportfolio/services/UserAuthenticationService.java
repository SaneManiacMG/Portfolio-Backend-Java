package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.helpers.PasswordValidator;
import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.interfaces.IUserDetailsService;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    @Autowired
    private IUserDetailsService userDetailsService;

    public UserAuthenticationService(IUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Object authenticateUser(AuthRequest authRequest) {
        return authRequest;
    }

    @Override
    public Object setPassword(AuthRequest authRequest) {
        List<String> errors = PasswordValidator.validatePassword(authRequest.getPassword());
        if (!errors.isEmpty()) {
            return errors;
        }

        return userDetailsService.changePassword(authRequest.getUserId(), authRequest.getPassword());
    }

    @Override
    public Object changeAccountStatus(String userId, AccountStatus status) {
        return userDetailsService.updateUserStatus(userId, status);
    }

    @Override
    public Object changeAccountRole(String userId, AccountRole role) {
        return userDetailsService.updateUserRole(userId, role);
    }

}
