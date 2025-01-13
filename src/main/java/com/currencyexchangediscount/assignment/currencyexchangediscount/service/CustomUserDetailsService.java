package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import com.currencyexchangediscount.assignment.currencyexchangediscount.config.CustomUserDetails;
import com.currencyexchangediscount.assignment.currencyexchangediscount.entity.User;
import com.currencyexchangediscount.assignment.currencyexchangediscount.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Custom implementation of the {@link UserDetailsService} interface that loads user-specific data.
 * <p>
 * This service is used for loading user details based on the username provided. It fetches the user information
 * from the database and returns it as a {@link CustomUserDetails} object, which is used by Spring Security for authentication and authorization.
 * </p>
 * <p>
 * If the user is not found, it throws a {@link UsernameNotFoundException}.
 * </p>
 *
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor for initializing the CustomUserDetailsService.
     *
     * @param userRepository The UserRepository to fetch user details from the database.
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by the given username. This method is called by Spring Security to authenticate users.
     * <p>
     * It looks up the user in the database by username and if found, returns the corresponding {@link CustomUserDetails}.
     * If the user is not found, it throws a {@link UsernameNotFoundException}.
     * </p>
     *
     * @param username The username of the user to load.
     * @return A {@link CustomUserDetails} object that contains the user's information.
     * @throws UsernameNotFoundException If the user is not found in the database.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user with username: {}", username);

        // Fetching user from the database by username
        User user = userRepository.findByUserName(username);

        // Checking if the user exists, if not throw an exception
        if (Objects.isNull(user)) {
            log.error("User with username {} not found", username);
            throw new UsernameNotFoundException("User not found");
        }

        log.info("User with username {} found, returning user details", username);
        return new CustomUserDetails(user);
    }
}
