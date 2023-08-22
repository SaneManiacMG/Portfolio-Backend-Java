package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import com.smworks.backendportfolio.models.requests.UserRequest;
import com.smworks.backendportfolio.models.responses.UserResponse;

import java.util.List;

 public interface IUserDetailsService {
     Object createUserAccount(UserRequest userDetails);
     Object getUserDetailsResponse(String userId);
     Object updateUserDetails(UserRequest userDetails);
     Object deleteUserDetails(String userId);
     List<UserResponse> getAllUserDetails();

     Object updateUserRole(String userId, AccountRole role);
     Object updateUserStatus(String userId, AccountStatus status);

     Object changePassword(String userId, String password);
}
