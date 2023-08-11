package com.smworks.backendportfolio.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetails extends User {
    @Column(unique = true)
    String username;

    @Column(unique = true)
    String email;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String phoneNr;
}
