package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.models.responses.AuthResponse;
import com.smworks.backendportfolio.repositories.RoleRepository;
import com.smworks.backendportfolio.security.JwtGenerator;
import com.smworks.backendportfolio.utils.PasswordValidator;
import com.smworks.backendportfolio.interfaces.IUserAuthenticationService;
import com.smworks.backendportfolio.models.entities.Role;
import com.smworks.backendportfolio.models.entities.UserEntity;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.AuthRequest;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    private AuthenticationManager authenticationManager;
    private JwtGenerator jwtGenerator;
    private UserEntityService userEntityService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserAuthenticationService(UserRepository userRepository,
            AuthenticationManager authenticationManager, JwtGenerator jwtGenerator,
            UserEntityService userEntityService, BCryptPasswordEncoder bCryptPasswordEncoder,
                                     RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userEntityService = userEntityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public AuthResponse authenticateUser(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserId(),
                        authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        System.out.println("User authenticated: " + authRequest.getUserId());
        return new AuthResponse(token);
    }

    @Override
    public Object setPassword(AuthRequest authRequest) {
        List<String> errors = PasswordValidator.validatePassword(authRequest.getPassword());
        if (!errors.isEmpty()) {
            return errors;
        }

        UserEntity userEntity = userEntityService.getUserDetails(authRequest.getUserId());
        if (userEntity == null) {
            return null;
        }

        userEntity.setPassword(bCryptPasswordEncoder.encode(authRequest.getPassword()));
        userEntity.setAccountStatus(AccountStatus.ACTIVE);

        Role userRole = roleRepository.findByAuthority("ROLE_USER").get();
        Role guestRole = roleRepository.findByAuthority("ROLE_GUEST").get();

        Set<Role> authorities = userEntity.getAuthorities();
        authorities.remove(guestRole);
        authorities.add(userRole);

        userEntity.setDateModified(LocalDateTime.now());

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "Password set successfully";
    }

    @Override
    public Object changeAccountStatus(String userId, AccountStatus status) {
        return null;
    }

    @Override
    public Object changeAccountRole(String userId, AccountRole role) {
        return null;
    }

}
