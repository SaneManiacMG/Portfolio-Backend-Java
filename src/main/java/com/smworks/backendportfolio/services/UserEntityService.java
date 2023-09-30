package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.utils.mappers.models.UserMapper;
import com.smworks.backendportfolio.utils.SequenceGenerator;
import com.smworks.backendportfolio.interfaces.IUserEntityService;
import com.smworks.backendportfolio.models.entities.*;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.models.responses.UserResponse;
import com.smworks.backendportfolio.repositories.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserEntityService implements IUserEntityService, UserDetailsService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private RoleRepository roleRepository;

    // TODO: Check and enforce order of identifiers, i.e. email, username, phone
    // TODO: Review my return statement for finding a speicific user

    public UserEntityService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public Object createUserEntity(UserRequest userRequest) {
        List<String> exceptions = getErrors(userRequest, null);
        if (!exceptions.isEmpty()) {
            return exceptions;
        }

        UserEntity userEntity = userMapper.mapUserRequestToUserEntity(userRequest);
        Set<Role> authorities = new HashSet<>();

        userEntity.setUserId(SequenceGenerator.generateUserId());
        userEntity.setPassword("Not Set");
        userEntity.setDateCreated(LocalDateTime.now());
        userEntity.setDateCreated(LocalDateTime.now());

        authorities.add(roleRepository.findByAuthority("ROLE_GUEST").get());
        userEntity.setAuthorities(authorities);

        userEntity.setAccountStatus(AccountStatus.UNVERIFIED);

        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            return e;
        }

        return userMapper.mapUserEntityToUserResponse(userEntity);
    }

    @Override
    public Object getUserEntityResponse(String userId) {
        UserEntity userEntity;

        userEntity = userRepository.findByEmail(userId);
        if (userEntity != null) {
            return userMapper.mapUserEntityToUserResponse(userEntity);
        }

        userEntity = userRepository.findByUsername(userId);
        if (userEntity != null) {
            return userMapper.mapUserEntityToUserResponse(userEntity);
        }

        userEntity = userRepository.findByPhoneNumber(userId);
        if (userEntity != null) {
            return userMapper.mapUserEntityToUserResponse(userEntity);
        }

        return null;
    }

    private List<String> getErrors(UserRequest request, String userId) {
        List<String> listOfErrors = new ArrayList<>();

        UserEntity userEntityByEmail = getUserDetails(request.getEmail());
        if (getUserDetails(request.getEmail()) != null && !userEntityByEmail.getUserId().equals(userId)) {
            listOfErrors.add("User with email '" + request.getEmail() + "' already exists");
        }

        UserEntity userEntityByUsername = getUserDetails(request.getUsername());
        if (getUserDetails(request.getUsername()) != null && !userEntityByUsername.getUserId().equals(userId)) {
            listOfErrors.add("User with username '" + request.getUsername() + "' already exists");
        }

        UserEntity userEntityByPhoneNumber = getUserDetails(request.getPhoneNumber());
        if (getUserDetails(request.getPhoneNumber()) != null && !userEntityByPhoneNumber.getUserId().equals(userId)) {
            listOfErrors.add("User with phone number '" + request.getPhoneNumber() + "' already exists");
        }
        return listOfErrors;
    }

    @Override
    public Object updateUserEntity(UserRequest userRequest) {
        UserEntity updatedUser = null;

        UserEntity userEntityByEmail = getUserDetails(userRequest.getEmail());
        if (userEntityByEmail != null) {
            try {
                updatedUser = userRepository.save(updateUserObject(userMapper.mapUserRequestToUserEntity(userRequest),
                        userEntityByEmail));
            } catch (DataIntegrityViolationException dive) {
                return getErrors(userRequest, userEntityByEmail.getUserId());
            } catch (Exception e) {
                System.out.println(e.getClass());
                return e;
            }
        }

        UserEntity userEntityByUsername = getUserDetails(userRequest.getUsername());
        if (userEntityByUsername != null) {
            try {
                updatedUser = userRepository.save(updateUserObject(
                        userMapper.mapUserRequestToUserEntity(userRequest), userEntityByUsername));
            } catch (DataIntegrityViolationException dive) {
                return getErrors(userRequest, userEntityByUsername.getUserId());
            } catch (Exception e) {
                return e;
            }
        }

        UserEntity userEntityByPhoneNumber = getUserDetails(userRequest.getPhoneNumber());
        if (userEntityByPhoneNumber != null) {
            try {
                updatedUser = userRepository.save(updateUserObject(
                        userMapper.mapUserRequestToUserEntity(userRequest), userEntityByPhoneNumber));
            } catch (DataIntegrityViolationException dive) {
                return getErrors(userRequest, userEntityByPhoneNumber.getUserId());
            } catch (Exception e) {
                return e;
            }
        }

        if (updatedUser == null) {
            return null;
        }

        return userMapper.mapUserEntityToUserResponse(updatedUser);
    }

    protected UserEntity updateUserObject(UserEntity updatedRecord, UserEntity oldRecord) {
        oldRecord.setFirstName(updatedRecord.getFirstName());
        oldRecord.setLastName(updatedRecord.getLastName());
        oldRecord.setPhoneNumber(updatedRecord.getPhoneNumber());
        oldRecord.setEmail(updatedRecord.getEmail());
        oldRecord.setUsername(updatedRecord.getUsername());
        oldRecord.setDateModified(LocalDateTime.now());
        return oldRecord;
    }

    @Override
    public Object deleteUserEntity(String userId) {
        UserEntity userEntity;

        if (userRepository.findByEmail(userId) != null) {
            userEntity = userRepository.findByEmail(userId);
        } else if (userRepository.findByUsername(userId) != null) {
            userEntity = userRepository.findByUsername(userId);
        } else if (userRepository.findByPhoneNumber(userId) != null) {
            userEntity = userRepository.findByPhoneNumber(userId);
        } else {
            return null;
        }

        try {
            userRepository.delete(userEntity);
        } catch (Exception e) {
            return e.getMessage();
        }

        return "User with id '" + userId + "' deleted successfully";
    }

    @Override
    public List<UserResponse> getAllUserEntities() {
        List<UserResponse> listOfUserResponses = new ArrayList<>();

        List<UserEntity> listOfUsers = userRepository.findAll();
        for (UserEntity user : listOfUsers) {
            listOfUserResponses.add(userMapper.mapUserEntityToUserResponse(user));
        }

        return listOfUserResponses;
    }

    protected UserEntity getUserDetails(String userId) {
        UserEntity userEntity;

        userEntity = userRepository.findByEmail(userId);
        if (userEntity != null) {
            return userEntity;
        }

        userEntity = userRepository.findByUsername(userId);
        if (userEntity != null) {
            return userEntity;
        }

        userEntity = userRepository.findByPhoneNumber(userId);
        if (userEntity != null) {
            return userEntity;
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = getUserDetails(username);
        if (findUserByUserId(userEntity.getUserId()) == null) {
            throw new UsernameNotFoundException("Username not found", null);
        }

        return userEntity;
    }

    @Override
    public UserEntity findUserByUserId(String userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.isPresent()) {
            return null;
        }

        System.out.println(userEntity.get());

        return userEntity.get();
    }

}
