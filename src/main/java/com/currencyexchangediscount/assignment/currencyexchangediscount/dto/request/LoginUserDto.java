package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request;

import lombok.Data;

/**
 * DTO class for handling user login request.
 * <p>
 * This class contains the login credentials for a user attempting to log in. It holds the user's
 * email address and password, which are required for authentication purposes.
 * </p>
 *
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@Data
public class LoginUserDto {

    /**
     * The email address of the user trying to log in.
     * <p>
     * This field holds the user's email, which is used as the unique identifier for the login process.
     * </p>
     */
    private String email;

    /**
     * The password of the user for authentication.
     * <p>
     * This field holds the password that the user enters to authenticate their identity.
     * It is expected to be securely validated during the login process.
     * </p>
     */
    private String password;

}
