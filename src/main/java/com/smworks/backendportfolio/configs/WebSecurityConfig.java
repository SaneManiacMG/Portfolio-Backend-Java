package com.smworks.backendportfolio.configs;

import com.smworks.backendportfolio.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**")
                                .hasAnyRole("USER", "ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/authentication/**")
                                .hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/createUser", "/loginUser", "/setPassword",
                                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**")
                                .permitAll())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
}