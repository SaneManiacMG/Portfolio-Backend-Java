package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.interfaces.IUserDetailsService;
import com.smworks.backendportfolio.models.entities.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService implements IUserDetailsService {
    @Override
    public UserDetails createUserAccount(UserDetails userDetails) {
        return null;
    }

    @Override
    public UserDetails getUserDetails(String userId) {
        return null;
    }

    @Override
    public UserDetails updateUserDetails(UserDetails userDetails) {
        return null;
    }

    @Override
    public UserDetails deleteUserDetails(UserDetails userDetails) {
        return null;
    }

    @Override
    public List<UserDetails> getAllUserDetails() {
        return null;
    }
}
