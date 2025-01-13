package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request.BillRequest;
import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response.BillResponse;
import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response.ItemResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Service responsible for generating the bill based on the provided bill request.
 * <p>
 * This service calculates the total amount for each item, applies the discounts based on user type and tenure,
 * and converts the amounts to the target currency. It also handles the bill's final discounts and prepares the
 * response for the client.
 * </p>
 *
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@Service
@Slf4j
public class BillService {

    private final DiscountService discountService;

    private final CurrencyExchangeService currencyExchangeService;

    private final List<String> excludedCategoriesForDiscount = List.of("GROCERIES");

    public BillService(DiscountService discountService, CurrencyExchangeService currencyExchangeService) {
        this.discountService = discountService;
        this.currencyExchangeService = currencyExchangeService;
    }

    /**
     * Generates a bill based on the given request, calculating item totals, applying discounts, and converting amounts
     * to the target currency.
     * <p>
     * This method processes each item in the bill request, calculates the total amounts, applies user-specific discounts,
     * and calculates the final bill in both the original and target currencies.
     * </p>
     *
     * @param billRequest The bill request containing the user details and item list.
     * @return A BillResponse containing the calculated details for the bill, including itemized responses and totals.
     */
    public BillResponse generateBill(BillRequest billRequest) {
        log.info("Generating bill for user type: {}, tenure: {}, original currency: {}, target currency: {}",
                billRequest.getUserType(), billRequest.getCustomerTenure(),
                billRequest.getOriginalCurrency(), billRequest.getTargetCurrency());

        // Atomic variables to store total amounts, allowing thread-safe updates
        AtomicReference<Double> originalCurrencyTotal = new AtomicReference<>(0.0);
        AtomicReference<Double> originalCurrencyTotalPercentageDiscount = new AtomicReference<>(0.0);

        // Get the exchange rate for currency conversion
        Double conversionRate = currencyExchangeService.getExchangeRate(
                billRequest.getOriginalCurrency(), billRequest.getTargetCurrency());
        log.info("Conversion rate from {} to {}: {}", billRequest.getOriginalCurrency(),
                billRequest.getTargetCurrency(), conversionRate);

        // Calculate the discount percentage based on the user type and tenure
        Double discountPercentage = discountService.calculateDiscountPercentage(
                billRequest.getUserType(), billRequest.getCustomerTenure());
        log.info("Calculated discount percentage for user type {}: {}", billRequest.getUserType(), discountPercentage);

        // Process each item in the bill request and calculate item-specific totals
        List<ItemResponse> itemResponses = billRequest.getItemRequestList().stream().map(itemRequest -> {
            double itemDiscount = itemRequest.getAmount() * discountPercentage * itemRequest.getQuantity();
            log.debug("Processing item: {}, Amount: {}, Discount: {}", itemRequest.getDescription(),
                    itemRequest.getAmount(), itemDiscount);

            // Build the item response and accumulate totals
            ItemResponse itemResponse = ItemResponse.builder()
                    .category(itemRequest.getCategory())
                    .conversionRate(conversionRate)
                    .originalCurrencyDiscount(excludedCategoriesForDiscount.contains(itemRequest.getCategory().toUpperCase()) ? 0 : itemDiscount)
                    .description(itemRequest.getDescription())
                    .originalCurrencyAmount(itemRequest.getAmount())
                    .quantity(itemRequest.getQuantity())
                    .build();

            // Update the original currency total and discount totals
            originalCurrencyTotal.updateAndGet(v -> v + itemResponse.getOriginalCurrencyPreDiscount());
            originalCurrencyTotalPercentageDiscount.updateAndGet(v -> v + itemResponse.getOriginalCurrencyDiscount());

            log.debug("Updated originalCurrencyTotal: {}", originalCurrencyTotal.get());
            log.debug("Updated originalCurrencyTotalPercentageDiscount: {}", originalCurrencyTotalPercentageDiscount.get());

            return itemResponse;
        }).toList();

        // Calculate the bill discount, 5% of the remaining amount after percentage discount
        Double originalCurrencyBillDiscount = (double) ((int) ((originalCurrencyTotal.get() - originalCurrencyTotalPercentageDiscount.get()) / 100)) * 5;
        log.info("Original currency total: {}, Total percentage discount: {}, Bill discount: {}",
                originalCurrencyTotal.get(), originalCurrencyTotalPercentageDiscount.get(), originalCurrencyBillDiscount);

        // Build and return the final BillResponse
        return BillResponse.builder()
                .itemResponseList(itemResponses)
                .originalCurrencyBillDiscount(originalCurrencyBillDiscount)
                .originalCurrencyTotalPercentageDiscount(originalCurrencyTotalPercentageDiscount.get())
                .conversionRate(conversionRate)
                .originalCurrencyTotalPreDiscount(originalCurrencyTotal.get())
                .build();
    }
}
