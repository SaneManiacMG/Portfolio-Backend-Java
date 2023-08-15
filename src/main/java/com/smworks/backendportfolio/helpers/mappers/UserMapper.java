package com.smworks.backendportfolio.helpers.mappers;

import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.models.responses.UserResponse;
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

    public UserResponse mapUserDetailsToUserResponse(UserDetails userDetails) {
        return modelMapper.map(userDetails, UserResponse.class);
    }
}
