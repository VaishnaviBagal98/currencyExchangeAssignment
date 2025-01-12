package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DTO class representing an individual item within a bill response.
 * <p>
 * This class contains information about an individual item, including its category, description,
 * amounts in the original and target currencies, quantity, and discounts. It also calculates various
 * totals and amounts, both pre- and post-discount, in both the original and target currencies.
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class ItemResponse {

    // Logger for logging relevant events or issues
    private static final Logger logger = LoggerFactory.getLogger(ItemResponse.class);

    /**
     * The category of the item (e.g., "electronics", "clothing").
     * <p>
     * This category can help in applying specific business logic or discounts to the item based on its type.
     * </p>
     */
    private String category;

    /**
     * The amount of the item in the original currency (before any discounts).
     * <p>
     * This is the unit price of the item in the original currency.
     * </p>
     */
    private Double originalCurrencyAmount;

    /**
     * The discount applied to the item in the original currency.
     * <p>
     * This is the amount of the discount applied to the item in the original currency.
     * </p>
     */
    private Double originalCurrencyDiscount;

    /**
     * The quantity of the item.
     * <p>
     * This represents the number of items purchased and is used to calculate the total price of the item
     * (before and after discounts).
     * </p>
     */
    private Double quantity;

    /**
     * A brief description of the item.
     * <p>
     * This is used for identifying the item in the bill, such as "Laptop", "Shirt", etc.
     * </p>
     */
    private String description;

    /**
     * The conversion rate from the original currency to the target currency.
     * <p>
     * This rate is used to convert the item's amount and discount from the original currency to the target currency.
     * </p>
     *
     * This field is ignored during JSON serialization to prevent exposing sensitive data.
     */
    @JsonIgnore
    private Double conversionRate;

    /**
     * The total amount of the item in the original currency before any discounts.
     * <p>
     * This is calculated as the unit price (`originalCurrencyAmount`) multiplied by the quantity.
     * </p>
     *
     * @return The pre-discount total amount in the original currency.
     */
    @JsonProperty("originalCurrencyPreDiscount")
    public Double getOriginalCurrencyPreDiscount() {
        Double preDiscountAmount = originalCurrencyAmount * quantity;
        logger.debug("Calculated originalCurrencyPreDiscount: {}", preDiscountAmount);
        return preDiscountAmount;
    }

    /**
     * The total amount of the item in the original currency, after applying the discount.
     * <p>
     * This is calculated by subtracting the discount (`originalCurrencyDiscount`) from the pre-discount amount.
     * </p>
     *
     * @return The total amount in the original currency after discount.
     */
    @JsonProperty("originalCurrencyTotal")
    private Double getOriginalCurrencyTotal() {
        Double totalAfterDiscount = getOriginalCurrencyPreDiscount() - originalCurrencyDiscount;
        logger.debug("Calculated originalCurrencyTotal: {}", totalAfterDiscount);
        return totalAfterDiscount;
    }

    /**
     * The total amount of the item in the target currency (before discount).
     * <p>
     * This is calculated by converting the original currency amount to the target currency using the conversion rate.
     * </p>
     *
     * @return The total amount in the target currency before discount.
     */
    @JsonProperty("targetCurrencyAmount")
    private Double getTargetCurrencyAmount() {
        Double targetCurrencyAmount = originalCurrencyAmount * conversionRate;
        logger.debug("Calculated targetCurrencyAmount: {}", targetCurrencyAmount);
        return targetCurrencyAmount;
    }

    /**
     * The discount applied to the item in the target currency.
     * <p>
     * This is calculated by converting the original discount to the target currency using the conversion rate.
     * </p>
     *
     * @return The discount in the target currency.
     */
    @JsonProperty("targetCurrencyDiscount")
    private Double getTargetCurrencyDiscount() {
        Double targetCurrencyDiscount = originalCurrencyDiscount * conversionRate;
        logger.debug("Calculated targetCurrencyDiscount: {}", targetCurrencyDiscount);
        return targetCurrencyDiscount;
    }

    /**
     * The total amount of the item in the target currency (after discount).
     * <p>
     * This is calculated by converting the original total to the target currency.
     * </p>
     *
     * @return The total amount in the target currency after discount.
     */
    @JsonProperty("targetCurrencyTotal")
    private Double getTargetCurrencyTotal(){
        Double targetCurrencyTotal = getOriginalCurrencyTotal() * conversionRate;
        logger.debug("Calculated targetCurrencyTotal: {}", targetCurrencyTotal);
        return targetCurrencyTotal;
    }

}
