package com.smworks.backendportfolio.models.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity extends UserBase {
    @Column(unique = true)
    String username;
    @Column(unique = true)
    String email;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "phone_number", unique = true)
    String phoneNumber;

    public UserEntity(String userId, String password, String username, String email, String firstName, String lastName,
            String phoneNumber) {
        super(userId, password);
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
