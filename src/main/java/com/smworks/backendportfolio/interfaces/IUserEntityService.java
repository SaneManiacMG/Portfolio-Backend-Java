package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.models.responses.UserResponse;

import java.util.List;

 public interface IUserEntityService {
     Object createUserEntity(UserRequest userRequest);
     Object getUserEntityResponse(String userId);
     Object updateUserEntity(UserRequest userRequest);
     Object deleteUserEntity(String userId);
     List<UserResponse> getAllUserEntities();

     Object updateUserRole(String userId, AccountRole role);
     Object updateUserStatus(String userId, AccountStatus status);

     Object changePassword(String userId, String password);
}
