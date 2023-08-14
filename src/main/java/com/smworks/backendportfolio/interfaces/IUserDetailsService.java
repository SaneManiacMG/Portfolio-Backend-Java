package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.entities.User;
import com.smworks.backendportfolio.models.entities.UserDetails;
import com.smworks.backendportfolio.models.requests.UserRequest;

import java.util.List;

public interface IUserDetailsService {
    public UserDetails createUserAccount(UserRequest userDetails);
    public UserDetails getUserDetails(String userId);
    public UserDetails updateUserDetails(UserRequest userDetails);
    public String deleteUserDetails(String userId);
    public List<UserDetails> getAllUserDetails();
}
