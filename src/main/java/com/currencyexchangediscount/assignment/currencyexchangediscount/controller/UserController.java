package com.currencyexchangediscount.assignment.currencyexchangediscount.controller;

import com.currencyexchangediscount.assignment.currencyexchangediscount.entity.User;
import com.currencyexchangediscount.assignment.currencyexchangediscount.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user-related operations like registration and login.
 * <p>
 * This controller exposes two API endpoints:
 * 1. POST /register: Registers a new user.
 * 2. POST /login: Verifies a user's credentials during login.
 * </p>
 *
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@RestController
@Slf4j
public class UserController {

    // Service layer that handles business logic for user operations
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to register a new user.
     * <p>
     * Accepts a User object in the request body, and calls the service to register the user.
     * </p>
     *
     * @param user The user object to be registered.
     * @return The registered user.
     */
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        log.info("Received request to register user: {}", user);

        // Call the service to register the user
        User registeredUser = userService.register(user);

        log.info("User registered successfully: {}", registeredUser);
        return registeredUser;
    }

    /**
     * Endpoint for user login.
     * <p>
     * Accepts a User object with credentials, and calls the service to verify the credentials.
     * </p>
     *
     * @param user The user object containing login credentials.
     * @return A string indicating the result of the login operation (e.g., success or failure).
     */
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        log.info("Received login request for user: {}", user);

        // Call the service to verify the user credentials
        String loginStatus = userService.verify(user);

        if ("success".equals(loginStatus)) {
            log.info("User login successful: {}", user);
        } else {
            log.warn("User login failed for: {}", user);
        }

        return loginStatus;
    }
}
