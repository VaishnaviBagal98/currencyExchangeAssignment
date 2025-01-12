package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO class that represents a request to calculate a bill.
 * <p>
 * This class contains the necessary data required to generate a bill. It includes information such
 * as the total amount, user type, customer tenure, currencies involved, and a list of items.
 * @author Vaishnavi Bagal
 * @version 1.0
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {

    /**
     * The total amount for which the bill needs to be generated.
     * <p>
     * This amount is typically the base amount that needs to be converted and billed according to
     * the exchange rate and other parameters.
     * </p>
     */
    private Double totalAmount;

    /**
     * The type of user requesting the bill (e.g., "regular" or "premium").
     * <p>
     * This value helps in applying specific logic or discounts depending on the user type.
     * </p>
     */
    private String userType;

    /**
     * The tenure of the customer, represented as a float value.
     * <p>
     * This could represent the number of months or years the customer has been with the service,
     * potentially affecting the discount or rate calculation.
     * </p>
     */
    private Float customerTenure;

    /**
     * The original currency of the total amount.
     * <p>
     * This is the currency in which the total amount is currently denominated.
     * </p>
     */
    private String originalCurrency;

    /**
     * The target currency to which the total amount needs to be converted.
     * <p>
     * This is the currency that the total amount is to be converted into.
     * </p>
     */
    private String targetCurrency;

    /**
     * A list of {@link ItemRequest} objects representing the individual items associated with the bill.
     * <p>
     * Each item in the list contains details such as item type, quantity, price, etc., and contributes to
     * the total amount.
     * </p>
     */
    private List<ItemRequest> itemRequestList;
}
