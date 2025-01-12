package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request.BillRequest;
import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request.ItemRequest;
import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response.BillResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class BillServiceTest {

    @InjectMocks
    private BillService billService;

    @Mock
    private DiscountService discountService;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateBill_validRequest_returnsCorrectBillResponse() {
        // Arrange
        BillRequest billRequest = createBillRequest("EMPLOYEE", 3.0, "USD", "EUR");
        ItemRequest itemRequest1 = new ItemRequest("ELECTRONICS", "Laptop", 1000.0, 1.0);
        ItemRequest itemRequest2 = new ItemRequest("GROCERIES", "Apple", 2.0, 5.0);

        billRequest.setItemRequestList(List.of(itemRequest1, itemRequest2));

        // Mock external services
        when(currencyExchangeService.getExchangeRate("USD", "EUR")).thenReturn(0.85);
        when(discountService.calculateDiscountPercentage("EMPLOYEE", 3.5F)).thenReturn(0.30);

        // Act
        BillResponse billResponse = billService.generateBill(billRequest);

        // Assert
        assertNotNull(billResponse);
        assertEquals(2, billResponse.getItemResponseList().size());
        assertEquals(0.0, billResponse.getOriginalCurrencyTotalPercentageDiscount(), 0.01);

        // Verify mock interactions
        verify(currencyExchangeService, times(1)).getExchangeRate("USD", "EUR");
        verify(discountService, times(1)).calculateDiscountPercentage("EMPLOYEE", 3.0F);
    }

    @Test
    void generateBill_invalidCurrencyConversion_returnsDefaultRate() {
        // Arrange
        BillRequest billRequest = createBillRequest("EMPLOYEE", 2.0, "USD", "AUD");
        ItemRequest itemRequest1 = new ItemRequest("ELECTRONICS", "Tablet", 300.0, 2.0);

        billRequest.setItemRequestList(List.of(itemRequest1));

        // Mock external services
        when(currencyExchangeService.getExchangeRate("USD", "AUD")).thenReturn(1.0);  // Simulate invalid rate

        // Act
        BillResponse billResponse = billService.generateBill(billRequest);

        // Assert
        assertNotNull(billResponse);
        assertEquals(1.0, billResponse.getConversionRate(), 0.01); // Should default to 1.0 due to invalid rate
    }

    private BillRequest createBillRequest(String userType, Double tenure, String originalCurrency, String targetCurrency) {
        BillRequest billRequest = new BillRequest();
        billRequest.setUserType(userType);
        billRequest.setCustomerTenure(tenure.floatValue());
        billRequest.setOriginalCurrency(originalCurrency);
        billRequest.setTargetCurrency(targetCurrency);
        return billRequest;
    }
}
