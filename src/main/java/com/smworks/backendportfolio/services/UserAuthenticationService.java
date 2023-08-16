package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.interfaces.IUserDetailsService;
import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import com.smworks.backendportfolio.models.responses.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    @Autowired
    private IUserDetailsService userDetailsService;

    public UserAuthenticationService(IUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Object authenticateUser(AuthRequest authRequest) {
        return null;
    }

    @Override
    public Object registerUser(AuthRequest authRequest) {

        return null;
    }

    @Override
    public Object resetPassword(AuthRequest authRequest) {
        return null;
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
