package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.helpers.PasswordValidator;
import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.interfaces.IUserEntityService;
import com.smworks.backendportfolio.models.entities.UserEntity;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    @Autowired
    private IUserEntityService userEntityService;

    @Autowired
    private UserRepository userRepository;

    public UserAuthenticationService(IUserEntityService userEntityService, UserRepository userRepository) {
        this.userEntityService = userEntityService;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails authenticateUser(AuthRequest authRequest) {
        UserEntity user = getUserEntity(authRequest.getUserId());
        return new User(user.getUserId(), user.getPassword(), user.getAccountRole());
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
        if (userByUsername != null) {
            return userByPhoneNumber;
        }

        return null;
    }

}
