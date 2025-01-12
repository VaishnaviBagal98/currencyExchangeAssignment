package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import com.currencyexchangediscount.assignment.currencyexchangediscount.entity.User;
import com.currencyexchangediscount.assignment.currencyexchangediscount.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for user-related operations such as registration and login.
 * This service handles the registration of new users, login verification, and JWT token generation.
 *
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Registers a new user. This method takes a user object, encrypts the password,
     * and saves the user in the repository.
     *
     * @param user The user to be registered.
     * @return The registered user with the encrypted password.
     * @throws RuntimeException if the registration fails due to repository issues.
     */
    public User register(User user) {
        logger.info("Attempting to register user with username: {}", user.getUserName());

        try {
            // Check if the username already exists
            if (userRepository.findByUserName(user.getUserName()) != null) {
                logger.warn("Username already taken: {}", user.getUserName());
                throw new IllegalArgumentException("Username already taken");
            }

            // Encrypt password before saving
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);

            logger.info("User registered successfully with username: {}", savedUser.getUserName());
            return savedUser;
        } catch (IllegalArgumentException e) {
            logger.error("User registration failed due to invalid input for username: {}", user.getUserName(), e);
            throw e; // Rethrow the exception with a custom message
        } catch (Exception e) {
            logger.error("Unexpected error occurred during user registration for username: {}", user.getUserName(), e);
            throw new RuntimeException("User registration failed", e);
        }
    }

    /**
     * Verifies user credentials by authenticating the username and password.
     * If authentication is successful, generates a JWT token for the user.
     *
     * @param user The user credentials to verify.
     * @return A JWT token if authentication is successful, otherwise "failure".
     * @throws RuntimeException if an error occurs during authentication or token generation.
     */
    public String verify(User user) {
        logger.info("Attempting to verify user with username: {}", user.getUserName());

        try {
            // Authenticate the user
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );

            if (authenticate.isAuthenticated()) {
                logger.info("User authenticated successfully: {}", user.getUserName());
                // Generate JWT token upon successful authentication
                return jwtService.generateToken(user);
            } else {
                logger.warn("Authentication failed for user: {}", user.getUserName());
                return "failure";
            }
        } catch (AuthenticationException e) {
            logger.error("Authentication error for username: {}", user.getUserName(), e);
            return "failure"; // Return failure message if authentication fails
        } catch (Exception e) {
            logger.error("Unexpected error occurred during login for username: {}", user.getUserName(), e);
            throw new RuntimeException("Login failed", e);
        }
    }
}
