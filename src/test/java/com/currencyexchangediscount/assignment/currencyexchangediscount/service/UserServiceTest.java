package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import com.currencyexchangediscount.assignment.currencyexchangediscount.entity.User;
import com.currencyexchangediscount.assignment.currencyexchangediscount.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserName("testuser");
        user.setPassword("password123");
    }

    @Test
    void testRegister() {
        // Arrange
        String encodedPassword = "encodedPassword";
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User result = userService.register(user);

        // Assert
        assertEquals("testuser", result.getUserName());
    }

}
