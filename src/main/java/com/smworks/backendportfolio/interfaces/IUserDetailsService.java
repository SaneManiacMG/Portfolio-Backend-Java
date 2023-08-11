package com.smworks.backendportfolio.interfaces;

import com.smworks.backendportfolio.models.entities.User;
import com.smworks.backendportfolio.models.entities.UserDetails;

public interface IUserDetailsService {
    public UserDetails createUserAccount(UserDetails userDetails);
    public UserDetails getUserDetails(UserDetails userDetails);
    public UserDetails updateUserDetails(UserDetails userDetails);
    public UserDetails deleteUserDetails(UserDetails userDetails);
}
