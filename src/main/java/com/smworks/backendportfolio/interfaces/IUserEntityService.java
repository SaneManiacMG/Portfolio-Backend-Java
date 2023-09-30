package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.entities.UserEntity;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.models.responses.UserResponse;

import java.util.List;

public interface IUserEntityService {
    Object createUserEntity(UserRequest userRequest);

    Object getUserEntityResponse(String userId);

    Object updateUserEntity(UserRequest userRequest);

    Object deleteUserEntity(String userId);

    List<UserResponse> getAllUserEntities();

    UserEntity findUserByUserId(String userId);
}
