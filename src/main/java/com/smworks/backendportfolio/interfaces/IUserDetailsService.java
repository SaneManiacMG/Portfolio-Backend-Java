package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.requests.UserRequest;

import java.util.List;

 public interface IUserDetailsService {
     Object createUserAccount(UserRequest userDetails);
     Object getUserDetails(String userId);
     Object updateUserDetails(UserRequest userDetails);
     Object deleteUserDetails(String userId);
     List<UserDetails> getAllUserDetails();
}
