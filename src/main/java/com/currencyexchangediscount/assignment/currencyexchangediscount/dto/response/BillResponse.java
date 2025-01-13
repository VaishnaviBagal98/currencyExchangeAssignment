package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * DTO class for representing the response to a bill calculation.
 * <p>
 * This class holds the details of the bill after calculation, including itemized responses, total amounts
 * before and after discounts, and final bill amounts in both the original and target currencies.
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class BillResponse {

    // Logger for logging relevant events or issues
    private static final Logger logger = LoggerFactory.getLogger(BillResponse.class);

    /**
     * A list of {@link ItemResponse} objects representing individual items within the bill.
     * <p>
     * Each item contains the details of the item such as description, amount, and any applicable discounts.
     * </p>
     */
    private List<ItemResponse> itemResponseList;

    /**
     * The total amount in the original currency before any discounts are applied.
     * <p>
     * This is the sum of all items before discounts are considered.
     * </p>
     */
    private Double originalCurrencyTotalPreDiscount;

    /**
     * The total percentage-based discount applied to the original currency total.
     * <p>
     * This discount is typically calculated as a percentage of the original currency total.
     * </p>
     */
    private Double originalCurrencyTotalPercentageDiscount;

    /**
     * The total bill discount applied to the original currency.
     * <p>
     * This is a fixed discount amount applied to the total bill in the original currency.
     * </p>
     */
    private Double originalCurrencyBillDiscount;

    /**
     * The conversion rate from the original currency to the target currency.
     * <p>
     * This rate is used to convert the final bill amounts from the original currency to the target currency.
     * </p>
     */
    private Double conversionRate;

    /**
     * The final bill amount in the original currency, after applying all discounts.
     * <p>
     * This is calculated as the total pre-discount amount minus the percentage discount and the bill discount.
     * </p>
     *
     * @return The final bill amount in the original currency.
     */
    @JsonProperty("originalCurrencyFinalBill")
    private Double getOriginalCurrencyFinalBill() {
        Double finalBill = originalCurrencyTotalPreDiscount - originalCurrencyTotalPercentageDiscount - originalCurrencyBillDiscount;
        logger.debug("Calculated originalCurrencyFinalBill: {}", finalBill);
        return finalBill;
    }

    /**
     * The total amount in the target currency before any discounts.
     * <p>
     * This is the original currency total, converted to the target currency using the conversion rate.
     * </p>
     *
     * @return The total pre-discount amount in the target currency.
     */
    @JsonProperty("targetedCurrencyTotalPreDiscount")
    private Double getTargetedCurrencyTotalPreDiscount() {
        Double totalPreDiscountTargetCurrency = originalCurrencyTotalPreDiscount * conversionRate;
        logger.debug("Calculated targetedCurrencyTotalPreDiscount: {}", totalPreDiscountTargetCurrency);
        return totalPreDiscountTargetCurrency;
    }

    /**
     * The total percentage discount applied to the original currency, converted to the target currency.
     * <p>
     * This is the percentage discount in the original currency, converted to the target currency.
     * </p>
     *
     * @return The percentage discount in the target currency.
     */
    @JsonProperty("targetedCurrencyTotalPercentageDiscount")
    private Double getTargetedCurrencyTotalPercentageDiscount() {
        Double percentageDiscountTargetCurrency = originalCurrencyTotalPercentageDiscount * conversionRate;
        logger.debug("Calculated targetedCurrencyTotalPercentageDiscount: {}", percentageDiscountTargetCurrency);
        return percentageDiscountTargetCurrency;
    }

    /**
     * The total bill discount applied to the original currency, converted to the target currency.
     * <p>
     * This is the fixed bill discount in the original currency, converted to the target currency.
     * </p>
     *
     * @return The bill discount in the target currency.
     */
    @JsonProperty("targetedCurrencyBillDiscount")
    private Double getTargetedCurrencyBillDiscount() {
        Double billDiscountTargetCurrency = originalCurrencyBillDiscount * conversionRate;
        logger.debug("Calculated targetedCurrencyBillDiscount: {}", billDiscountTargetCurrency);
        return billDiscountTargetCurrency;
    }

    /**
     * The final bill amount in the target currency, after applying all discounts.
     * <p>
     * This is calculated as the final bill amount in the original currency, converted to the target currency.
     * </p>
     *
     * @return The final bill amount in the target currency.
     */
    @JsonProperty("targetCurrencyFinalBill")
    private Double getTargetedCurrencyFinalBill() {
        Double finalBillTargetCurrency = getOriginalCurrencyFinalBill() * conversionRate;
        logger.debug("Calculated targetCurrencyFinalBill: {}", finalBillTargetCurrency);
        return finalBillTargetCurrency;
    }
}
