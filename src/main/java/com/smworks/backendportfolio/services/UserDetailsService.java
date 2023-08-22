package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.helpers.mappers.models.UserMapper;
import com.smworks.backendportfolio.helpers.SequenceGenerator;
import com.smworks.backendportfolio.interfaces.IUserDetailsService;
import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.models.responses.UserResponse;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsService implements IUserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // TODO: Check and enforce order of identifiers, i.e. email, username, phone number

    public UserDetailsService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Object createUserAccount(UserRequest userRequest) {
        List<String> exceptions = getErrors(userRequest, null);
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
    public Object getUserDetailsResponse(String userId) {
        UserDetails userDetails;

        userDetails = userRepository.findByEmail(userId);
        if (userDetails != null) {
            return userMapper.mapUserDetailsToUserResponse(userDetails);
        }

        userDetails = userRepository.findByUsername(userId);
        if (userDetails != null) {
            return userMapper.mapUserDetailsToUserResponse(userDetails);
        }

        userDetails = userRepository.findByPhoneNumber(userId);
        if (userDetails != null) {
            return userMapper.mapUserDetailsToUserResponse(userDetails);
        }

        return null;
    }

    private List<String> getErrors(UserRequest request, String userId) {
        List<String> listOfErrors = new ArrayList<>();

        UserDetails userDetailsByEmail = getUserDetails(request.getEmail());
        if (getUserDetails(request.getEmail()) != null && !userDetailsByEmail.getUserId().equals(userId)) {
            listOfErrors.add("User with email '" + request.getEmail() + "' already exists");
        }

        UserDetails userDetailsByUsername = getUserDetails(request.getUsername());
        if (getUserDetails(request.getUsername()) != null && !userDetailsByUsername.getUserId().equals(userId)) {
            listOfErrors.add("User with username '" + request.getUsername() + "' already exists");
        }

        UserDetails userDetailsByPhoneNumber = getUserDetails(request.getPhoneNumber());
        if (getUserDetails(request.getPhoneNumber()) != null && !userDetailsByPhoneNumber.getUserId().equals(userId)) {
            listOfErrors.add("User with phone number '" + request.getPhoneNumber() + "' already exists");
        }
        return listOfErrors;
    }

    @Override
    public Object updateUserDetails(UserRequest userRequest) {
        UserDetails updatedUser;

        UserDetails userDetailsByEmail = getUserDetails(userRequest.getEmail());
        if (userDetailsByEmail != null) {
            try {
                updatedUser = userRepository.save(updateUserObject(userMapper.mapUserRequestToUserDetails(userRequest),
                        userDetailsByEmail));
            } catch (DataIntegrityViolationException dive) {
                return getErrors(userRequest, userDetailsByEmail.getUserId());
            } catch (Exception e) {
                System.out.println(e.getClass());
                return e;
            }
            return updatedUser;
        }

        UserDetails userDetailsByUsername = getUserDetails(userRequest.getUsername());
        if (userDetailsByUsername != null) {

            try {
                updatedUser = userRepository.save(updateUserObject(
                        userMapper.mapUserRequestToUserDetails(userRequest), userDetailsByUsername));
            } catch (DataIntegrityViolationException dive) {
                return getErrors(userRequest, userDetailsByUsername.getUserId());
            } catch (Exception e) {
                return e;
            }
            return updatedUser;
        }

        UserDetails userDetailsByPhoneNumber = getUserDetails(userRequest.getPhoneNumber());
        if (userDetailsByPhoneNumber != null) {
            try {
                updatedUser = userRepository.save(updateUserObject(
                        userMapper.mapUserRequestToUserDetails(userRequest), userDetailsByPhoneNumber));
            } catch (DataIntegrityViolationException dive) {
                return getErrors(userRequest, userDetailsByPhoneNumber.getUserId());
            } catch (Exception e) {
                return e;
            }
            return updatedUser;
        }
        return null;
    }

    private UserDetails updateUserObject(UserDetails updatedRecord, UserDetails oldRecord) {
        oldRecord.setFirstName(updatedRecord.getFirstName());
        oldRecord.setLastName(updatedRecord.getLastName());
        oldRecord.setPhoneNumber(updatedRecord.getPhoneNumber());
        oldRecord.setEmail(updatedRecord.getEmail());
        oldRecord.setUsername(updatedRecord.getUsername());
        oldRecord.setDateModified(LocalDateTime.now());
        return oldRecord;
    }

    @Override
    public Object deleteUserDetails(String userId) {
        UserDetails userDetails;

        if (userRepository.findByEmail(userId) != null) {
            userDetails = userRepository.findByEmail(userId);
        }
        else if (userRepository.findByUsername(userId) != null) {
            userDetails = userRepository.findByUsername(userId);
        }
        else if (userRepository.findByPhoneNumber(userId) != null){
            userDetails = userRepository.findByPhoneNumber(userId);
        } else {
            return null;
        }

        try {
            userRepository.delete(userDetails);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "User with id '" + userId + "' deleted successfully";
    }

    @Override
    public List<UserResponse> getAllUserDetails() {
        List<UserResponse> listOfUserResponses = new ArrayList<>();

        List<UserDetails> listOfUsers = userRepository.findAll();
        for (UserDetails user : listOfUsers) {
            listOfUserResponses.add(userMapper.mapUserDetailsToUserResponse(user));
        }

        return listOfUserResponses;
    }

    @Override
    public Object updateUserRole(String userId, AccountRole role) {
        UserDetails userDetails = getUserDetails(userId);

        if (userDetails == null) {
            return null;
        }

        userDetails.setAccountRole(role);
        userDetails.setDateModified(LocalDateTime.now());

        try {
            userRepository.save(userDetails);
        } catch (Exception e) {
            return e;
        }
        return userMapper.mapUserDetailsToUserResponse(userDetails);
    }

    @Override
    public Object updateUserStatus(String userId, AccountStatus status) {
        UserDetails userDetails = getUserDetails(userId);

        if (userDetails == null) {
            return null;
        }

        userDetails.setAccountStatus(status);
        userDetails.setDateModified(LocalDateTime.now());

        try {
            userRepository.save(userDetails);
        } catch (Exception e) {
            return e;
        }
        return userMapper.mapUserDetailsToUserResponse(userDetails);
    }

    @Override
    public Object changePassword(String userId, String password) {
        UserDetails userDetails = getUserDetails(userId);
        if (userDetails == null) {
            return null;
        }

        userDetails.setPassword(bCryptPasswordEncoder.encode(password));
        userDetails.setPassword(password);
        userDetails.setDateModified(LocalDateTime.now());

        try {
            userRepository.save(userDetails);
        } catch (Exception e) {
            return e;
        }
        return "Password updated successfully";
    }

    private UserDetails getUserDetails(String userId) {
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
}
