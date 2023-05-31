package com.smworks.backendportfolio.servicetests;

import com.smworks.backendportfolio.models.User;
import com.smworks.backendportfolio.repositories.UserRepository;
import com.smworks.backendportfolio.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    User user1, user2, user3, user4, user5;
    List<User> users = new ArrayList<>();


    @BeforeEach
    public void setup() {
        user1 = new User("11111", "SaneManiacMG",
                "Mogomotsi", "Moroane",
                "mmoroane@hotmail.com", "0813916607", "ADMIN",true);
        user2 = new User("22222", "dummy1admin",
                "dummy1", "admin",
                "dummy1@email.com", "0123456789", "ADMIN",true);
        users.add(user1);
        users.add(user2);
        userRepository.save(user1);
    }

    @Test
    public void testFindAllUsers_OK() {
        when(userRepository.findAll()).thenReturn(users);

        ResponseEntity<Object> response = userServiceImpl.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testFindAllUsers_NOT_FOUND() {
        List<User> emptyList = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(emptyList);

        ResponseEntity<Object> response = userServiceImpl.getAllUsers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No users found", response.getBody());
    }

    @Test
    public void testFindUserByUserId_FOUND() {
        when(userRepository.findById(user1.getUserId())).thenReturn(Optional.of(user1));
        when(userRepository.existsById(user1.getUserId())).thenReturn(true);
        ResponseEntity<Object> response = userServiceImpl.findByUserId(user1);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(user1.toString(), response.getBody().toString());
    }

    @Test
    public void testFindUserById_NOT_FOUND() {
        when(userRepository.findById(user1.getUserId())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = userServiceImpl.findByUserId(user1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User ID not found", response.getBody());
    }

    @Test
    public void testFindUserByUsername_FOUND() {
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.of(user1));

        ResponseEntity<Object> response = userServiceImpl.findByUsername(user1);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(user1.toString(), response.getBody().toString());
    }

    @Test
    public void testFindUserByUsername_NOT_FOUND() {
        when(userRepository.findByUsername(user1.getUsername())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = userServiceImpl.findByUsername(user1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Username not found", response.getBody());
    }

    @Test
    public void testFindUserByEmail_FOUND() {
        when(userRepository.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));
        ResponseEntity<Object> response = userServiceImpl.findByEmail(user1);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(user1.toString(), response.getBody().toString());
    }

    @Test
    public void testFindUserByEmail_NOT_FOUND() {
        when(userRepository.findByEmail(user1.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<Object> response = userServiceImpl.findByEmail(user1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Email not found", response.getBody());
    }

    @Test
    public void testUpdateUserDetails_OK() {
        user1.setUsername("MG");

        when(userRepository.findById(user1.getUserId())).thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(user1)).thenReturn(user1);

        ResponseEntity<Object> response = userServiceImpl.updateUserDetails(user1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user1.toString(), response.getBody().toString());
    }

    @Test
    public void testUpdateUserDetails_NOT_FOUND() {
        when(userRepository.findById("RandomId")).thenReturn(Optional.empty());

        ResponseEntity<Object> response = userServiceImpl.updateUserDetails(user1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    public void testUpdateUserDetails_CONFLICT() {
        User existingUser = new User();
        existingUser.setEmail(user1.getEmail());
        existingUser.setUserId(user1.getUserId());
        existingUser.setUsername(user1.getUsername());

        when(userRepository.findById(existingUser.getUserId())).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(user1));
        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(user1));

        ResponseEntity<Object> response = userServiceImpl.updateUserDetails(existingUser);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Username/email already taken", response.getBody());
    }

    @Test
    public void testDeleteUserRecord_OK() {
        when(userRepository.existsById(user1.getUserId())).thenReturn(true);

        doNothing().when(userRepository).deleteById(user1.getUserId());

        ResponseEntity<Object> response = userServiceImpl.deleteUserRecord(user1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted", response.getBody());

        verify(userRepository, times(1)).existsById(user1.getUserId());
        verify(userRepository, times(1)).deleteById(user1.getUserId());
    }

    @Test
    public void testDeleteUserRecord_NOT_FOUND() {
        when(userRepository.findById("RandomId")).thenReturn(Optional.empty());

        ResponseEntity<Object> response = userServiceImpl.deleteUserRecord(user1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    @Test
    public void testCreateUserRecord_CREATED() {
        user3 = new User("GENERATED_ID", "TestAcc1",
                "Test", "Acc",
                "test1@email.com", "0123456789", "TEST",true);

        when(userRepository.findByEmail(user3.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(user3.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(user3)).thenReturn(user3);

        ResponseEntity<Object> response = userServiceImpl.createUserRecord(user3);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user3.toString(), response.getBody().toString());
    }

    @Test
    public void testCreateUserRecord_CONFLICT() {
        user3 = new User();
        user3.setUsername(user1.getUsername());
        user3.setEmail(user1.getUsername());
        user3.setUserId("RandomId");

        when(userRepository.findByEmail(user3.getEmail())).thenReturn(Optional.of(user1));
        when(userRepository.findByUsername(user3.getUsername())).thenReturn(Optional.of(user1));

        ResponseEntity<Object> response = userServiceImpl.createUserRecord(user3);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("Username/email already taken", response.getBody().toString());
    }
}
