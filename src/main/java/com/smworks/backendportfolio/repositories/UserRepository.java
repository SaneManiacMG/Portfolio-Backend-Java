package com.smworks.backendportfolio.repositories;

import com.smworks.backendportfolio.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);

    UserEntity findByUsername(String userId);

    UserEntity findByPhoneNumber(String phoneNumber);
}
