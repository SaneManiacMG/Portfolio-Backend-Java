package com.smworks.backendportfolio.configs;

import com.smworks.backendportfolio.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filerChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                        .antMatchers("**/loginUser", "**/setPassword", "**/createUser").permitAll()
                        .antMatchers("**/users/**").hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                        .antMatchers("**accountRole/**").hasAnyRole("ADMIN", "SUPER_ADMIN"))
                .authorizeRequests(auth -> auth
                        .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}
