package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class CurrencyExchangeServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    private CurrencyExchangeService currencyExchangeService;

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;

    private Map<String, Object>  mockResponse;

    @BeforeEach
    void setUp() {
        currencyExchangeService = new CurrencyExchangeService();
        mockResponse = Map.of(
                "result", "success",
                "base_code", "USD",
                "conversion_rates", Map.of(
                        "USD", 1.0,
                        "EUR", 0.89,
                        "INR", 82.64
                )
        );
        // Inject the mocked RestTemplate
        Mockito.when(restTemplate.getForObject(anyString(), Mockito.eq(Map.class))).thenReturn(mockResponse);
    }

    @Test
    void testGetExchangeRates() {
        // Arrange
        String baseCurrency = "USD";
        String expectedUrl = String.format("%s/latest/%s?apikey=%s", apiUrl, baseCurrency, apiKey);

        // Act
        Map<String, Object> exchangeRates = currencyExchangeService.getExchangeRates(baseCurrency);

        // Assert
        assertThat(exchangeRates).isNotNull();
        assertThat(exchangeRates.get("result")).isEqualTo("success");
        assertThat(exchangeRates.get("base_code")).isEqualTo("USD");

        // Verify the call was made to the correct URL
        Mockito.verify(restTemplate).getForObject(expectedUrl, Map.class);
    }
}
