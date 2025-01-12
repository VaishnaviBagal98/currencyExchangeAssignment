package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class CurrencyExchangeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks // Automatically injects the mocked RestTemplate
    private CurrencyExchangeService currencyExchangeService;

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;

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

        // Mock the RestTemplate behavior
        Mockito.when(restTemplate.getForObject(anyString(), Mockito.eq(Map.class))).thenReturn(mockResponse);
    }

    @Test
    void testGetExchangeRates_ShouldReturnSuccess() {
        // Arrange
        String baseCurrency = "USD";
        String expectedUrl = String.format("%s/latest/%s?apikey=%s", apiUrl, baseCurrency, apiKey);

        // Act
        Map<String, Object> exchangeRates = currencyExchangeService.getExchangeRates(baseCurrency);

        // Assert
        assertThat(exchangeRates).isNotNull();
        assertThat(exchangeRates.get("result")).isEqualTo("success");
        assertThat(exchangeRates.get("base_code")).isEqualTo(baseCurrency);

    }
}
