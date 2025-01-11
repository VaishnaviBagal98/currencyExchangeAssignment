package com.currencyexchangediscount.assignment.currencyexchangediscount.controller;

import com.currencyexchangediscount.assignment.currencyexchangediscount.service.CurrencyExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class CurrencyExchangeControllerUnitTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    private Map<String, Object> mockResponse;

    @BeforeEach
    void setUp() {
        mockResponse = Map.of(
                "result", "success",
                "base_code", "USD",
                "conversion_rates", Map.of(
                        "USD", 1.0,
                        "EUR", 0.89,
                        "INR", 82.64
                )
        );
    }

    @Test
    void testGetExchangeRates() throws Exception {
        // Mocking the service response
        Mockito.when(currencyExchangeService.getExchangeRates(anyString())).thenReturn(mockResponse);

        // Perform the GET request
        mockMvc.perform(get("/api/exchange-rates/USD")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.base_code").value("USD"))
                .andExpect(jsonPath("$.conversion_rates.USD").value(1.0))
                .andExpect(jsonPath("$.conversion_rates.EUR").value(0.89))
                .andExpect(jsonPath("$.conversion_rates.INR").value(82.64));
    }
}
