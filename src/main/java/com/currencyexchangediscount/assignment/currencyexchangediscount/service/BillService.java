package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request.BillRequest;
import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response.BillResponse;
import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BillService {

    @Autowired
    private DiscountService discountService;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    public BillResponse generateBill(BillRequest billRequest) {
        AtomicReference<Double> originalCurrencyTotal = new AtomicReference<>(0.0);
        AtomicReference<Double> originalCurrencyTotalPercentageDiscount = new AtomicReference<>(0.0);
        Double conversionRate = currencyExchangeService.
                getExchangeRate(billRequest.getOriginalCurrency(), billRequest.getTargetCurrency());
        Double discountPercentage = discountService.calculateDiscountPercentage(billRequest.getUserType(),billRequest.getCustomerTenure());

        List<ItemResponse> itemResponses = billRequest.getItemRequestList().stream().map((itemRequest)-> {
            ItemResponse itemResponse = ItemResponse.builder()
                    .category(itemRequest.getCategory())
                    .conversionRate(conversionRate)
                    .originalCurrencyDiscount(itemRequest.getAmount()*discountPercentage)
                    .description(itemRequest.getDescription())
                    .originalCurrencyAmount(itemRequest.getAmount())
                    .quantity(itemRequest.getQuantity())
                    .build();
            originalCurrencyTotal.updateAndGet(v -> v + itemResponse.getOriginalCurrencyAmount());
            originalCurrencyTotalPercentageDiscount.updateAndGet(v -> v + itemResponse.getOriginalCurrencyDiscount());
            return itemResponse;
        }).toList();

       Double originalCurrencyBillDiscount = (double) ( (int) (originalCurrencyTotal.get() / 100)) * 5;
       originalCurrencyTotal.updateAndGet(v-> v - originalCurrencyBillDiscount);

        return BillResponse.builder()
               .itemResponseList(itemResponses)
               .originalCurrencyBillDiscount(originalCurrencyBillDiscount)
               .originalCurrencyTotal(originalCurrencyTotal.get())
               .originalCurrencyTotalPercentageDiscount(originalCurrencyTotalPercentageDiscount.get())
               .conversionRate(conversionRate)
               .build();

}


}
