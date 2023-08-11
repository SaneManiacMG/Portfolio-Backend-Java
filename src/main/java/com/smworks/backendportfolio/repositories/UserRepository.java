package com.smworks.backendportfolio.repositories;

import com.smworks.backendportfolio.models.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, String> { }
