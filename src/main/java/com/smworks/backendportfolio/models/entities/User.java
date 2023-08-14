package com.smworks.backendportfolio.models.entities;

import com.smworks.backendportfolio.models.enums.AccountRole;
import com.smworks.backendportfolio.models.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class User {
    @Id
    String userId;

    @Column(nullable = false)
    String password;

    @Column(name = "date_created")
    LocalDateTime dateCreated;

    @Column(name = "date_modified")
    LocalDateTime dateModified;

    @Column(name = "account_role")
    AccountRole accountRole;

    @Column(name = "account_status")
    AccountStatus accountStatus;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.accountRole = AccountRole.USER;
        this.accountStatus = AccountStatus.ACTIVE;
    }
}
