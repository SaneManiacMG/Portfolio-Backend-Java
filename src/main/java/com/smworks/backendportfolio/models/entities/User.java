package com.smworks.backendportfolio.models.entities;

import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
public class User {
    @Id
    String userId;
    @Column(nullable = false)
    String password;
    @Column(name = "date_created")
    LocalDateTime dateCreated;
    @Column(name = "date_modified")
    LocalDateTime dateModified;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_role")
    AccountRole accountRole;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    AccountStatus accountStatus;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.accountRole = AccountRole.GUEST;
        this.accountStatus = AccountStatus.UNVERIFIED;
    }
}
