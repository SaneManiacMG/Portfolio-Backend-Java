package com.smworks.backendportfolio.services;

import com.smworks.backendportfolio.models.entities.UserDetailsImpl;
import com.smworks.backendportfolio.models.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserEntityService userEntityService;

    @Autowired
    public UserDetailsServiceImpl(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityService.getUserDetails(userId);
        if (userEntity == null)
            throw new UsernameNotFoundException("User not found " + userId);
        return new UserDetailsImpl(userEntity);
    }
}