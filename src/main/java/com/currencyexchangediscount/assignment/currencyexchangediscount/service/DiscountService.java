package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service responsible for calculating the discount percentage based on user type and tenure.
 * <p>
 * This service provides a method to calculate the discount percentage for a user based on their user type (e.g., employee, affiliate)
 * and their tenure with the organization. The discount percentage is calculated based on predefined rules:
 * - Employees get a 30% discount.
 * - Affiliates get a 10% discount.
 * - Users with a tenure greater than 2 years receive a 5% discount.
 * </p>
 *
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@Service
@Slf4j
public class DiscountService {

    /**
     * Calculates the discount percentage based on the user type and tenure.
     * <p>
     * The method applies the following discount rules:
     * - 30% for employees.
     * - 10% for affiliates.
     * - 5% if the user has a tenure of more than 2 years.
     * If no conditions are met, no discount is applied.
     * </p>
     *
     * @param userType The type of the user (e.g., EMPLOYEE, AFFILIATE).
     * @param tenure   The tenure (in years) of the user with the organization.
     * @return The calculated discount percentage as a double (e.g., 0.30 for 30%).
     */
    public double calculateDiscountPercentage(String userType, Float tenure) {
        log.info("Calculating discount percentage for user type: {} with tenure: {} years", userType, tenure);

        // Check for employee user type
        switch (userType.toUpperCase()) {
            case "EMPLOYEE":
                log.debug("User is an employee. Applying 30% discount.");
                return 0.30;
            case "AFFILIATE":
                log.debug("User is an affiliate. Applying 10% discount.");
                return 0.10;
            default:
                log.debug("User is neither an employee nor an affiliate.");
        }

        // Check if tenure qualifies for a discount
        if (tenure > 2) {
            log.debug("User has a tenure of more than 2 years. Applying 5% discount.");
            return 0.05;
        }

        // No discount applicable if none of the above conditions are met
        log.debug("No discount applicable for the given user type and tenure.");
        return 0;
    }
}
