package com.smworks.backendportfolio.servicetests;

import com.smworks.backendportfolio.models.Login;
import com.smworks.backendportfolio.models.LoginRequest;
import com.smworks.backendportfolio.models.User;
import com.smworks.backendportfolio.repositories.LoginRepository;
import com.smworks.backendportfolio.repositories.UserRepository;
import com.smworks.backendportfolio.services.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoginServiceTest {
    @Mock
    private LoginRepository loginRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LoginServiceImpl loginService;
    private User user;
    private Login login, login2;
    private LoginRequest request1, request2;

    @BeforeEach
    public void setup() {
        user = new User("GeneratedSequence", "Dummy2User", "Dummy", "Two",
                "dummy-two@email.com", "0129982254", "TEST", true);
        userRepository.save(user);
        login = new Login("GeneratedSequence", "123456789", true);
        loginRepository.save(login);
    }

    @Test
    public void authenticateTest_OK() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(loginRepository.findById(user.getUserId())).thenReturn(Optional.of(login));

        LoginRequest request1 = new LoginRequest(user.getUsername(), login.getPassword());
        LoginRequest request2 = new LoginRequest(user.getEmail(), login.getPassword());

        ResponseEntity<Object> response1 = loginService.authenticate(request1);
        ResponseEntity<Object> response2 = loginService.authenticate(request2);

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
    }

    @Test
    public void authenticateTest_NOT_ACCEPTABLE() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(loginRepository.findById(user.getUserId())).thenReturn(Optional.of(login));

        LoginRequest request1 = new LoginRequest("", login.getPassword());
        LoginRequest request2 = new LoginRequest(user.getEmail(), "");
        LoginRequest request3 = new LoginRequest(user.getUsername(), "");
        LoginRequest request4 = new LoginRequest("", "");

        ResponseEntity<Object> response1 = loginService.authenticate(request1);
        ResponseEntity<Object> response2 = loginService.authenticate(request2);
        ResponseEntity<Object> response3 = loginService.authenticate(request3);
        ResponseEntity<Object> response4 = loginService.authenticate(request4);

        assertEquals("Missing value/s", response1.getBody());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response1.getStatusCode());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response2.getStatusCode());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response3.getStatusCode());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, response4.getStatusCode());
    }

    @Test
    public void authenticateTest_UNAUTHORIZED() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(loginRepository.findById(user.getUserId())).thenReturn(Optional.of(login));

        LoginRequest request1 = new LoginRequest(user.getUsername(), "123123123");
        LoginRequest request2 = new LoginRequest(user.getEmail(), "123123123");
        LoginRequest request3 = new LoginRequest("Random Value", login.getPassword());

        ResponseEntity<Object> response1 = loginService.authenticate(request1);
        ResponseEntity<Object> response2 = loginService.authenticate(request2);
        ResponseEntity<Object> response3 = loginService.authenticate(request3);

        assertEquals("Invalid username/password", response1.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response1.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, response2.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED, response3.getStatusCode());

    }

    @Test
    public void authenticateTest_FORBIDDEN() {
        login2 = new Login("GeneratedSequence", "123456789", false);

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(loginRepository.findById(user.getUserId())).thenReturn(Optional.of(login2));

        LoginRequest request1 = new LoginRequest(user.getUsername(), login2.getPassword());
        LoginRequest request2 = new LoginRequest(user.getEmail(), login2.getPassword());

        ResponseEntity<Object> response1 = loginService.authenticate(request1);
        ResponseEntity<Object> response2 = loginService.authenticate(request2);

        assertEquals("Account locked", response1.getBody());
        assertEquals("Account locked", response2.getBody());
        assertEquals(HttpStatus.FORBIDDEN, response1.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, response2.getStatusCode());

    }
}
