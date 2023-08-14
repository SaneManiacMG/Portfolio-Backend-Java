package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.configs.mappers.UserMapper;
import com.smworks.backendportfolio.interfaces.IUserDetailsService;
import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService implements IUserDetailsService {
    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDetailsService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails createUserAccount(UserRequest userRequest) {
        UserDetails userDetails = userMapper.mapUserRequestToUserDetails(userRequest);
        return userRepository.save(userDetails);
    }

    @Override
    public UserDetails getUserDetails(String userId) {
        return userRepository.getReferenceById(userId);
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
