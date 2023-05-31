package com.smworks.backendportfolio.servicetests;

import com.smworks.backendportfolio.models.Login;
import com.smworks.backendportfolio.models.RegisterRequest;
import com.smworks.backendportfolio.models.User;
import com.smworks.backendportfolio.repositories.LoginRepository;
import com.smworks.backendportfolio.repositories.UserRepository;
import com.smworks.backendportfolio.services.RegisterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegisterServiceTest {
    User user;
    @Mock
    private UserRepository userRepository;
    @Mock
    private LoginRepository loginRepository;
    @InjectMocks
    private RegisterServiceImpl registerService;

    @BeforeEach
    public void setup() {
        user = new User("Username1", "Username1", "Test", "Account",
                "test@email.com", "0129982254", "TEST", true);
        userRepository.save(user);
    }

    @Test
    public void registerNewUserTest_CREATED() {
        RegisterRequest request = new RegisterRequest(user.getUsername(), user.getEmail(),
                "123456789");
        Login savedLogin = new Login(user.getUsername(), request.getPassword(), true);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(loginRepository.save(savedLogin)).thenReturn(savedLogin);

        ResponseEntity<Object> response = registerService.createNewUserLogin(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedLogin.toString(), response.getBody().toString());
    }

    @Test
    public void registerNewUserTest_UNAUTHORIZED() {
        RegisterRequest request1 = new RegisterRequest(user.getUsername(), "RandomEmail", "123456");
        RegisterRequest request2 = new RegisterRequest("RandomUsername", user.getEmail(), "123456");

        when(userRepository.findByUsername(request1.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(request2.getEmail())).thenReturn(Optional.of(user));

        ResponseEntity<Object> response1 = registerService.createNewUserLogin(request1);
        ResponseEntity<Object> response2 = registerService.createNewUserLogin(request2);

        assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, response2.getStatusCode());
        assertEquals("Invalid user credentials", response1.getBody());

     }

    @Test
    public void registerNewUserTest_NOT_ACCEPTABLE() {
        RegisterRequest request1 = new RegisterRequest(user.getUsername(), "", "123456");
        RegisterRequest request2 = new RegisterRequest("", user.getEmail(), "123456");
        RegisterRequest request3 = new RegisterRequest(user.getUsername(), user.getEmail(), "");

        ResponseEntity<Object> response1 = registerService.createNewUserLogin(request1);
        ResponseEntity<Object> response2 = registerService.createNewUserLogin(request2);
        ResponseEntity<Object> response3 = registerService.createNewUserLogin(request3);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, response1.getStatusCode());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response2.getStatusCode());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response3.getStatusCode());
        assertEquals("Missing value/s", response1.getBody());

    }
}
