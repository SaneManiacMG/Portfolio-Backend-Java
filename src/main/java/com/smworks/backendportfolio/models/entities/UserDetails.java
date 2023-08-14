package com.smworks.backendportfolio.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserDetails extends User {
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

    public UserDetails(String userId, String password, String username, String email, String firstName, String lastName, String phoneNumber) {
        super(userId, password);
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
