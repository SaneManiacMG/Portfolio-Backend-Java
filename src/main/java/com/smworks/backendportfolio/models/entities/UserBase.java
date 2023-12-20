package com.smworks.backendportfolio.models.entities;

import com.smworks.backendportfolio.models.enums.AccountStatus;
import lombok.Data;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@MappedSuperclass
public class UserBase implements UserDetails {
    @Id
    String userId;
    @Column(nullable = false)
    String password;
    @Column(name = "date_created")
    LocalDateTime dateCreated;
    @Column(name = "date_modified")
    LocalDateTime dateModified;

    @Column(name = "authorities")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role_junction", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    AccountStatus accountStatus;

    public UserBase() {
        super();
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.authorities = new HashSet<Role>();
        this.accountStatus = AccountStatus.UNVERIFIED;
    }

    public UserBase(String userId, String password) {
        super();
        this.userId = userId;
        this.password = password;
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.authorities = new HashSet<Role>();
        this.accountStatus = AccountStatus.UNVERIFIED;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
