package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.models.responses.AuthResponse;
import com.smworks.backendportfolio.repositories.RoleRepository;
import com.smworks.backendportfolio.security.JwtGenerator;
import com.smworks.backendportfolio.utils.PasswordValidator;
import com.smworks.backendportfolio.utils.mappers.models.UserMapper;
import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.entities.Role;
import com.smworks.backendportfolio.models.entities.UserEntity;
import com.smworks.backendportfolio.models.enums.*;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import com.smworks.backendportfolio.models.requests.PasswordResetRequest;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    private AuthenticationManager authenticationManager;
    private JwtGenerator jwtGenerator;
    private UserEntityService userEntityService;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;

    public UserAuthenticationService(UserRepository userRepository,
            AuthenticationManager authenticationManager, JwtGenerator jwtGenerator,
            UserEntityService userEntityService, PasswordEncoder passwordEncoder,
            RoleRepository roleRepository, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userEntityService = userEntityService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Object authenticateUser(AuthRequest authRequest) {
        UserEntity userEntity = userEntityService.getUserDetails(authRequest.getUserId());
        if (userEntity == null) {
            return null;
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserId(),
                            authRequest.getPassword()));
            String token = jwtGenerator.generateToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new AuthResponse(token);
        } catch (AuthenticationException e) {
            return e;
        }
    }

    @Override
    public Object setPassword(PasswordResetRequest passwordResetRequest) {
        // TODO: Modify password reset method, it currently just resets the password
        // without checking the current password
        List<String> errors = PasswordValidator.validatePassword(passwordResetRequest.getNewPassword());
        if (!errors.isEmpty()) {
            return errors;
        }

        UserEntity userEntity = userEntityService.getUserDetails(passwordResetRequest.getUserId());
        if (userEntity == null) {
            return null;
        }

        userEntity.setPassword(passwordEncoder.encode(passwordResetRequest.getNewPassword()));
        userEntity.setAccountStatus(AccountStatus.ACTIVE);

        Role userRole = roleRepository.findByAuthority("ROLE_" + AccountRole.USER.name()).get();
        Role guestRole = roleRepository.findByAuthority("ROLE_" + AccountRole.GUEST.name()).get();

        Set<Role> authorities = userEntity.getAuthorities();

        if (authorities.contains(guestRole)) {
            authorities.remove(guestRole);
        }

        authorities.add(userRole);

        userEntity.setDateModified(LocalDateTime.now());

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return e;
        }

        return "Password set for user " + passwordResetRequest.getUserId() + " successfully";
    }

    @Override
    public Object changeAccountStatus(String userId, AccountStatus status) {
        UserEntity userEntity = userEntityService.getUserDetails(userId);
        if (userEntity == null) {
            return null;
        }

        userEntity.setAccountStatus(status);
        userEntity.setDateModified(LocalDateTime.now());

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return e;
        }

        return userMapper.mapUserEntityToUserResponse(userEntity);
    }

    @Override
    public Object removeAccountRole(String userId, AccountRole role) {
        UserEntity userEntity = userEntityService.getUserDetails(userId);
        if (userEntity == null) {
            return null;
        }

        Set<Role> authorities = userEntity.getAuthorities();
        Role userRole = roleRepository.findByAuthority("ROLE_" + role.name()).get();
        authorities.remove(userRole);
        userEntity.setDateModified(LocalDateTime.now());

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return e;
        }

        return role.name() + " role removed successfully for " + userId;
    }

    @Override
    public Object addAccountRole(String userId, AccountRole role) {
        UserEntity userEntity = userEntityService.getUserDetails(userId);
        if (userEntity == null) {
            return null;
        }

        Role userRole = roleRepository.findByAuthority("ROLE_" + role.name()).get();
        userEntity.getAuthorities().add(userRole);
        userEntity.setDateModified(LocalDateTime.now());

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return e;
        }

        return role.name() + " role added successfully for " + userId;
    }
}
