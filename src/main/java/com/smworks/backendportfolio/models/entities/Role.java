package com.smworks.backendportfolio.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Integer roleId;

    private String authority;

    public Role() {
        super();
    }

    public Role(String authority) {
        super();
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
