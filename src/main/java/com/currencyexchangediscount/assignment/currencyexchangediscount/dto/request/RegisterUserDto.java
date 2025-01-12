package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request;

import lombok.Data;


/**
 * @author Vaishnavi Bagal
 * @version 1.0
 */

@Data
public class RegisterUserDto {
    private String email;
    private String password;
    private String name;
}
