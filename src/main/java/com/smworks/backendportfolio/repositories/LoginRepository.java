package com.smworks.backendportfolio.repositories;

import com.smworks.backendportfolio.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, String> {

}
