package com.smworks.backendportfolio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.smworks.backendportfolio.models.entities.Role;
import com.smworks.backendportfolio.repositories.RoleRepository;

@SpringBootApplication
public class BackendPortfolioApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendPortfolioApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() > 0) {
                return;
            }

            roleRepository.save(new Role("ROLE_GUEST"));
            roleRepository.save(new Role("ROLE_USER"));
            roleRepository.save(new Role("ROLE_ADMIN"));
            roleRepository.save(new Role("ROLE_SUPER_ADMIN"));
        };
    }
}
