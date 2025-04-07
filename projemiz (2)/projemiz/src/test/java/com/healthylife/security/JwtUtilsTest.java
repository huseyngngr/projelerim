package com.healthylife.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    private Authentication authentication;
    private UserDetails userDetails;
    private String jwtSecret;
    private int jwtExpirationMs;

    @BeforeEach
    void setUp() {
        jwtSecret = "testSecretKey";
        jwtExpirationMs = 86400000; // 24 hours

        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", jwtSecret);
        ReflectionTestUtils.setField(jwtUtils, "jwtExpirationMs", jwtExpirationMs);

        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    void generateJwtToken_ShouldGenerateValidToken() {
        String token = jwtUtils.generateJwtToken(authentication);

        assertNotNull(token);
        assertTrue(jwtUtils.validateJwtToken(token));
        assertEquals("test@example.com", jwtUtils.getUserNameFromJwtToken(token));
    }

    @Test
    void validateJwtToken_WithValidToken_ShouldReturnTrue() {
        String token = jwtUtils.generateJwtToken(authentication);
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void validateJwtToken_WithInvalidToken_ShouldReturnFalse() {
        assertFalse(jwtUtils.validateJwtToken("invalid.token.here"));
    }

    @Test
    void getUserNameFromJwtToken_ShouldReturnUsername() {
        String token = jwtUtils.generateJwtToken(authentication);
        assertEquals("test@example.com", jwtUtils.getUserNameFromJwtToken(token));
    }

    @Test
    void generateJwtToken_ShouldIncludeExpiration() {
        String token = jwtUtils.generateJwtToken(authentication);
        assertTrue(jwtUtils.validateJwtToken(token));

        // Wait for token to expire
        try {
            Thread.sleep(jwtExpirationMs + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertFalse(jwtUtils.validateJwtToken(token));
    }
} 