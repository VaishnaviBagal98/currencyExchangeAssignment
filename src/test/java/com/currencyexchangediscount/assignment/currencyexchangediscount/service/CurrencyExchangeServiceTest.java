package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyExchangeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CacheManager cacheManager;

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    public void setUp() {
        cacheManager = new ConcurrentMapCacheManager("exchangeRates");
        currencyExchangeService = new CurrencyExchangeService();
    }

    @Test
    public void testGetExchangeRates_success() {
        // Given
        String baseCurrency = "USD";
        String url = "https://open.er-api.com/v6/latest/USD?apikey=b42152b3c8054b014b0fe388";
        Map<String, Object> mockResponse = Map.of(
                "rates", Map.of("EUR", 0.85, "GBP", 0.75)
        );
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        // When
        Map<String, Object> result = currencyExchangeService.getExchangeRates(baseCurrency);

        // Then
        assertNotNull(result);
        assertTrue(result.containsKey("rates"));
        assertEquals(0.85, ((Map<String, Double>) result.get("rates")).get("EUR"));
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));
    }

    @Test
    public void testGetExchangeRates_failure() {
        // Given
        String baseCurrency = "USD";
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenThrow(new RuntimeException("API call failed"));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> {
            currencyExchangeService.getExchangeRates(baseCurrency);
        });

        assertEquals("API call failed", exception.getMessage());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));
    }

    @Test
    public void testGetExchangeRate_cachedValue() {
        // Given
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        Map<String, Object> mockResponse = Map.of(
                "rates", Map.of("EUR", 0.85, "GBP", 0.75)
        );
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        // First call to get exchange rate (should call the API)
        double rate1 = currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);

        // Second call to get exchange rate (should use cached value)
        double rate2 = currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);

        // Then
        assertEquals(0.85, rate1);
        assertEquals(0.85, rate2); // The rate should be cached
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class)); // Only one API call
    }

    @Test
    public void testGetExchangeRate_defaultRate_whenNotFound() {
        // Given
        String baseCurrency = "USD";
        String targetCurrency = "AUD"; // This rate will not be available in the mocked response
        Map<String, Object> mockResponse = Map.of(
                "rates", Map.of("EUR", 0.85, "GBP", 0.75)
        );
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        // When
        double result = currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);

        // Then
        assertEquals(1.0, result); // Default rate when not found
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));
    }

    @Test
    public void testGetExchangeRate_foundRate() {
        // Given
        String baseCurrency = "USD";
        String targetCurrency = "EUR";
        Map<String, Object> mockResponse = Map.of(
                "rates", Map.of("EUR", 0.85, "GBP", 0.75)
        );
        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        // When
        double result = currencyExchangeService.getExchangeRate(baseCurrency, targetCurrency);

        // Then
        assertEquals(0.85, result); // The rate for EUR should be returned
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Map.class));
    }
}
