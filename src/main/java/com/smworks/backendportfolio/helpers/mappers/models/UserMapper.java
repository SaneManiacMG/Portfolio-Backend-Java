package com.smworks.backendportfolio.helpers.mappers.models;

import com.smworks.backendportfolio.models.entities.UserEntity;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.models.responses.AuthResponse;
import com.smworks.backendportfolio.models.responses.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntity mapUserRequestToUserEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, UserEntity.class);
    }

    public UserResponse mapUserEntityToUserResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponse.class);
    }

    public AuthResponse mapUserEntityToAuthResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, AuthResponse.class);
    }
}
