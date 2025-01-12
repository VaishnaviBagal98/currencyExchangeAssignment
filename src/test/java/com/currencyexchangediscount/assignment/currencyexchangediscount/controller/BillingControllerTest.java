package com.currencyexchangediscount.assignment.currencyexchangediscount.controller;

import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request.BillRequest;
import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response.BillResponse;
import com.currencyexchangediscount.assignment.currencyexchangediscount.service.BillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for BillingController class.
 */
class BillingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BillService billService;

    @InjectMocks
    private BillingController billingController;

    private BillRequest billRequest;
    private BillResponse billResponse;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(billingController).build();

        // Prepare sample data for BillRequest and BillResponse
        billRequest = BillRequest.builder()
                .userType("EMPLOYEE")
                .customerTenure(3.0f)
                .originalCurrency("USD")
                .targetCurrency("INR")
                .build();

        billResponse = BillResponse.builder()
                .originalCurrencyTotalPreDiscount(100.0)
                .originalCurrencyTotalPercentageDiscount(30.0)
                .originalCurrencyBillDiscount(10.0)
                .conversionRate(75.0)
                .itemResponseList(null)  // Can be mocked further if needed
                .build();
    }

    /**
     * Test case for successful bill calculation.
     * This test verifies that the controller correctly calls the service and returns the correct response.
     */
    @Test
    void testGetBill_Success() throws Exception {
        // Arrange
        when(billService.generateBill(any(BillRequest.class))).thenReturn(billResponse);

        // Act and Assert
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(billRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.originalCurrencyTotalPreDiscount").value(100.0))
                .andExpect(jsonPath("$.originalCurrencyTotalPercentageDiscount").value(30.0))
                .andExpect(jsonPath("$.originalCurrencyBillDiscount").value(10.0))
                .andExpect(jsonPath("$.conversionRate").value(75.0));

        // Verify that the service method was called exactly once
        verify(billService, times(1)).generateBill(any(BillRequest.class));
    }

    /**
     * Test case for when an error occurs during bill calculation.
     * This test verifies that the controller handles exceptions properly and returns an internal server error (500).
     */
    @Test
    void testGetBill_Error() throws Exception {
        // Arrange
        when(billService.generateBill(any(BillRequest.class))).thenThrow(new RuntimeException("Error occurred"));

        // Act and Assert
        mockMvc.perform(post("/api/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(billRequest)))
                .andExpect(status().isInternalServerError());

        // Verify that the service method was called exactly once
        verify(billService, times(1)).generateBill(any(BillRequest.class));
    }
}
