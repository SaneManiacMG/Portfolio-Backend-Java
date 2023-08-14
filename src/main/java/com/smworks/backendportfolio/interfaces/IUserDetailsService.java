package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.entities.User;
import com.smworks.backendportfolio.models.entities.UserDetails;

import java.util.List;

public interface IUserDetailsService {
    public UserDetails createUserAccount(UserDetails userDetails);
    public UserDetails getUserDetails(String userId);
    public UserDetails updateUserDetails(UserDetails userDetails);
    public UserDetails deleteUserDetails(UserDetails userDetails);
    public List<UserDetails> getAllUserDetails();
}
