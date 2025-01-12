package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import com.currencyexchangediscount.assignment.currencyexchangediscount.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    private JwtService jwtService;

    @Mock
    private User mockUser;

    @Mock
    private UserDetails mockUserDetails;

    @BeforeEach
    public void setUp() {
        // Initialize the JwtService and mock objects
        jwtService = new JwtService();
        mockUser = mock(User.class);
        mockUserDetails = mock(UserDetails.class);
    }

    @Test
    void testGenerateToken() {
        // Given
        when(mockUser.getUserName()).thenReturn("testUser");

        // When
        String token = jwtService.generateToken(mockUser);

        // Then
        assertNotNull(token); // Ensure the token is generated
        assertTrue(token.startsWith("eyJ")); // A basic check to ensure it's a JWT token
    }

    @Test
    void testIsTokenValid_ExpiredToken() {
        // Given: Token with expiration date in the past
        String expiredToken = generateExpiredToken();

        // When
        boolean isValid = jwtService.isTokenValid(expiredToken, mockUserDetails);

        // Then
        assertFalse(isValid); // The token should be invalid since it's expired
    }

    private String generateExpiredToken() {
        User expiredUser = new User();
        expiredUser.setUserName("testUser");
        expiredUser.setPassword("password123");

        String expiredToken = jwtService.generateToken(expiredUser);

        // Adjust the expiration date to a past date
        long expiredTime = System.currentTimeMillis() - 60 * 60 * 1000; // 1 hour ago
        return expiredToken.replace("exp", String.valueOf(expiredTime));
    }
}
