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
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    String userId;

    @Column
    String password;

    @Column
    LocalDateTime dateCreated;

    @Column
    LocalDateTime dateModified;

    @Column
    AccountRole accountRole;

    @Column
    AccountStatus accountStatus;
}
