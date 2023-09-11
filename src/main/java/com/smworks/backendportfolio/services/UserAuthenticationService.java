package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.utils.PasswordValidator;
import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.interfaces.IUserEntityService;
import com.smworks.backendportfolio.models.entities.UserEntity;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    private IUserEntityService userEntityService;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserAuthenticationService(IUserEntityService userEntityService, UserRepository userRepository,
                                     AuthenticationManager authenticationManager) {
        this.userEntityService = userEntityService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails authenticateUser(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserId(),
                        authRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        if (authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    @Override
    public Object setPassword(AuthRequest authRequest) {
        List<String> errors = PasswordValidator.validatePassword(authRequest.getPassword());
        if (!errors.isEmpty()) {
            return errors;
        }

        return null;
    }

    @Override
    public Object changeAccountStatus(String userId, AccountStatus status) {
        return null;
    }

    @Override
    public Object changeAccountRole(String userId, AccountRole role) {
        return null;
    }

    private UserEntity getUserEntity(String userId) {
        UserEntity userByEmail = userRepository.findByEmail(userId);
        if (userByEmail != null) {
            return userByEmail;
        }

        UserEntity userByUsername = userRepository.findByUsername(userId);
        if (userByUsername != null) {
            return userByUsername;
        }

        UserEntity userByPhoneNumber = userRepository.findByPhoneNumber(userId);
        if (userByPhoneNumber != null) {
            return userByPhoneNumber;
        }

        return null;
    }

}
