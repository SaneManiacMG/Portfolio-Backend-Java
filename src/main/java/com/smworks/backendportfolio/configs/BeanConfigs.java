package com.smworks.backendportfolio.configs;

import com.smworks.backendportfolio.security.JwtAuthEntryPoint;
import com.smworks.backendportfolio.services.UserDetailsServiceImpl;
import com.smworks.backendportfolio.services.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfigs {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
