package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for DiscountService class.
 */
class DiscountServiceTest {

    private DiscountService discountService;

    @BeforeEach
    void setUp() {
        discountService = new DiscountService();
    }

    /**
     * Test case for employee user type, expecting a 30% discount.
     */
    @Test
    void testCalculateDiscountPercentage_forEmployee() {
        // Given
        String userType = "EMPLOYEE";
        Float tenure = 1.0f;

        // When
        double discount = discountService.calculateDiscountPercentage(userType, tenure);

        // Then
        assertEquals(0.30, discount, "Expected a 30% discount for employees");
    }

    /**
     * Test case for affiliate user type, expecting a 10% discount.
     */
    @Test
    void testCalculateDiscountPercentage_forAffiliate() {
        // Given
        String userType = "AFFILIATE";
        Float tenure = 1.0f;

        // When
        double discount = discountService.calculateDiscountPercentage(userType, tenure);

        // Then
        assertEquals(0.10, discount, "Expected a 10% discount for affiliates");
    }

    /**
     * Test case where tenure is greater than 2 years, expecting a 5% discount.
     */
    @Test
    void testCalculateDiscountPercentage_forTenureGreaterThan2() {
        // Given
        String userType = "CUSTOMER";  // Non-employee, non-affiliate
        Float tenure = 3.0f;

        // When
        double discount = discountService.calculateDiscountPercentage(userType, tenure);

        // Then
        assertEquals(0.05, discount, "Expected a 5% discount for users with more than 2 years of tenure");
    }

    /**
     * Test case where tenure is less than or equal to 2 years and user type is neither employee nor affiliate, expecting no discount.
     */
    @Test
    void testCalculateDiscountPercentage_forNoDiscount() {
        // Given
        String userType = "CUSTOMER";  // Non-employee, non-affiliate
        Float tenure = 1.0f;

        // When
        double discount = discountService.calculateDiscountPercentage(userType, tenure);

        // Then
        assertEquals(0.0, discount, "Expected no discount for a non-employee, non-affiliate with tenure less than 2 years");
    }

    /**
     * Test case where user type is mixed case, expecting a 30% discount.
     */
    @Test
    void testCalculateDiscountPercentage_forEmployeeMixedCase() {
        // Given
        String userType = "eMPlOyEe";  // Mixed case
        Float tenure = 5.0f;

        // When
        double discount = discountService.calculateDiscountPercentage(userType, tenure);

        // Then
        assertEquals(0.30, discount, "Expected a 30% discount for employees even with mixed case user type");
    }

    /**
     * Test case where user type is not employee or affiliate, but tenure is more than 2 years, expecting a 5% discount.
     */
    @Test
    void testCalculateDiscountPercentage_forTenureGreaterThan2_withNonEmployeeType() {
        // Given
        String userType = "GUEST";  // Non-employee, non-affiliate
        Float tenure = 3.5f;

        // When
        double discount = discountService.calculateDiscountPercentage(userType, tenure);

        // Then
        assertEquals(0.05, discount, "Expected a 5% discount for users with more than 2 years of tenure even with a non-employee user type");
    }

}
