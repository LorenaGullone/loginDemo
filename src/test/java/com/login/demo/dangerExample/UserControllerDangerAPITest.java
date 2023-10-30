package com.login.demo.dangerExample;

import com.login.demo.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerDangerAPITest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testLogin_UserFound() {
        String baseUrl = "http://localhost:" + port + "/home/login";
        UserDTO user = new UserDTO("testUser", "testPassword");

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, user, String.class);

        // Verifica che la risposta HTTP sia stata ricevuta con successo
        org.junit.jupiter.api.Assertions.assertEquals(200, response.getStatusCodeValue());

        // Verifica il contenuto della risposta
        org.junit.jupiter.api.Assertions.assertTrue(Objects.requireNonNull(response.getBody()).contains("testUser"));
    }

    @Test
    public void testLogin_UserNotFound() {
        String baseUrl = "http://localhost:" + port + "/home/login";
        UserDTO user = new UserDTO("nonExistentUser", "testPassword");

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, user, String.class);

        // Verifica che la risposta HTTP sia uno stato di conflitto
        org.junit.jupiter.api.Assertions.assertEquals(409, response.getStatusCodeValue());

        // Verifica il contenuto della risposta
        org.junit.jupiter.api.Assertions.assertTrue(Objects.requireNonNull(response.getBody()).contains("no account associated"));
    }

    @Test
    public void testRegistration_Success() {
        String baseUrl = "http://localhost:" + port + "/home/registration";
        UserDTO user = new UserDTO("newUser", "password");

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, user, String.class);

        // Verifica che la risposta HTTP sia stata ricevuta con successo
        org.junit.jupiter.api.Assertions.assertEquals(200, response.getStatusCodeValue());

        // Verifica il contenuto della risposta
        org.junit.jupiter.api.Assertions.assertTrue(Objects.requireNonNull(response.getBody()).contains("newUser"));
    }

    @Test
    public void testRegistration_UserAlreadyExists() {
        String baseUrl = "http://localhost:" + port + "/home/registration";
        UserDTO user = new UserDTO("existingUser", "password");

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, user, String.class);

        // Verifica che la risposta HTTP sia uno stato di errore
        org.junit.jupiter.api.Assertions.assertEquals(400, response.getStatusCodeValue());

        // Verifica il contenuto della risposta
        org.junit.jupiter.api.Assertions.assertTrue(Objects.requireNonNull(response.getBody()).contains("EXISTS"));
    }
}

