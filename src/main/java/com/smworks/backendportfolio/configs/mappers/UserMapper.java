package com.smworks.backendportfolio.configs.mappers;

import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.requests.UserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDetails mapUserRequestToUserDetails(UserRequest userRequest) {
        return modelMapper.map(userRequest, UserDetails.class);
    }
}
