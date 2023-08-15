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
import java.util.ArrayList;
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
        List<String> exceptions = getErrors(userRequest);
        if (!exceptions.isEmpty()) {
            return exceptions;
        }

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
            return e;
        }
    }

    @Override
    public Object getUserDetails(String userId) {
        UserDetails userDetails;

        userDetails = userRepository.findByEmail(userId);
        if (userDetails != null) {
            return userDetails;
        }

        userDetails = userRepository.findByUsername(userId);
        if (userDetails != null) {
            return userDetails;
        }

        userDetails = userRepository.findByPhoneNumber(userId);
        if (userDetails != null) {
            return userDetails;
        }

        return null;
    }

    private List<String> getErrors(UserRequest request) {
        List<String> listOfErrors = new ArrayList<>();

        if (getUserDetails(request.getEmail()) != null) {
            listOfErrors.add("User with email '" + request.getEmail() + "' already exists");
        }
        if (getUserDetails(request.getUsername()) != null) {
            listOfErrors.add("User with username '" + request.getUsername() + "' already exists");
        }
        if (getUserDetails(request.getPhoneNumber()) != null) {
            listOfErrors.add("User with phone number '" + request.getPhoneNumber() + "' already exists");
        }
        return listOfErrors;
    }

    @Override
    public UserDetails updateUserDetails(UserRequest userRequest) {
        UserDetails userDetails = userMapper.mapUserRequestToUserDetails(userRequest);
        return userRepository.save(userDetails);
    }

    @Override
    public String deleteUserDetails(String userId) {
        UserDetails userDetails;

        if (getUserDetails(userId) == null) {
            return "User with id: " + userId + " does not exist";
        } else {
            userDetails = (UserDetails) getUserDetails(userId);
        }

        userRepository.deleteById(userDetails.getUserId());

        return "User with id: " + userId + " deleted successfully";
    }

    @Override
    public List<UserDetails> getAllUserDetails() {
        return userRepository.findAll();
    }
}
