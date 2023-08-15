package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.helpers.mappers.UserMapper;
import com.smworks.backendportfolio.helpers.SequenceGenerator;
import com.smworks.backendportfolio.interfaces.IUserDetailsService;
import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserDetailsService implements IUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthenticationService userAuthenticationService;
    private final UserMapper userMapper;

    public UserDetailsService(UserRepository userRepository, UserMapper userMapper,
                              UserAuthenticationService userAuthenticationService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    public Object createUserAccount(UserRequest userRequest) {
        UserDetails userDetails = userMapper.mapUserRequestToUserDetails(userRequest);

        userDetails.setUserId(SequenceGenerator.generateUserId());
        userDetails.setPassword("Not Set");
        userDetails.setDateCreated(LocalDateTime.now());
        userDetails.setDateCreated(LocalDateTime.now());
        userDetails.setAccountRole(AccountRole.GUEST);
        userDetails.setAccountStatus(AccountStatus.UNVERIFIED);

        try {
            userRepository.save(userDetails);
            return userMapper.mapUserDetailsToUserResponse(userDetails);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDetails getUserDetails(String userId) {
        System.out.println("User Id: " + userId);
        try {
            return userRepository.findById(userId).orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserDetails updateUserDetails(UserRequest userRequest) {
        UserDetails userDetails = userMapper.mapUserRequestToUserDetails(userRequest);
        return userRepository.save(userDetails);
    }

    @Override
    public String deleteUserDetails(String userId) {
        userRepository.deleteById(userId);
        return "User with id: " + userId + " deleted successfully";
    }

    @Override
    public List<UserDetails> getAllUserDetails() {
        return userRepository.findAll();
    }
}
