package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class CurrencyExchangeServiceTest {

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CacheManager cacheManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetExchangeRates_Success() {
        // Arrange
        String baseCurrency = "USD";
        Map<String, Object> mockResponse = Map.of(
                "rates", Map.of("EUR", 0.85, "GBP", 0.75)
        );

        when(restTemplate.getForObject(any(), any()))
                .thenReturn(mockResponse);

        // Act
        Map<String, Object> response = currencyExchangeService.getExchangeRates(baseCurrency);

        // Assert
        assertNotNull(response);
        assertTrue(response.containsKey("rates"));
        Map<String, Double> rates = (Map<String, Double>) response.get("rates");
        assertEquals(0.97, rates.get("EUR"), 0.10);
    }
}
