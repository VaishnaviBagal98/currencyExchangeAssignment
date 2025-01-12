package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class representing an individual item within a bill request.
 * <p>
 * This class holds the details of a specific item that is part of the bill. It includes the category,
 * description, amount, and quantity of the item, which contribute to the total bill calculation.
 * </p>
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    /**
     * The category of the item (e.g., "electronics", "clothing", "food").
     * <p>
     * This category can be used to apply specific business rules or discounts for different types of items.
     * </p>
     * @version 1.0
     */
    private String category;

    /**
     * A description of the item (e.g., "Laptop", "T-shirt", "Pizza").
     * <p>
     * The description provides a brief identifier for the item being billed.
     * </p>
     */
    private String description;

    /**
     * The amount of money associated with a single unit of the item.
     * <p>
     * This is the price per unit of the item before considering quantity or discounts.
     * </p>
     */
    private Double amount;

    /**
     * The quantity of the item being purchased.
     * <p>
     * The quantity will be multiplied by the item's amount to calculate the total cost for this item.
     * </p>
     */
    private Double quantity;

}
