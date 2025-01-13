package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Service responsible for fetching exchange rates for currency conversion.
 * This service provides methods to retrieve the exchange rates from an external API and cache them for performance optimization.
 * It can fetch the conversion rate between two currencies and store them in the cache to avoid redundant API calls.
 */
@Service
public class CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);

    private final RestTemplate restTemplate;

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;

    private final CacheManager cacheManager;

    /**
     * Constructor to initialize the CurrencyExchangeService.
     * Creates a new instance of RestTemplate to make HTTP requests.
     */
    public CurrencyExchangeService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Retrieves the exchange rates for a given base currency from an external API.
     * This method fetches exchange rates for the given base currency from an external API and returns the data in a Map format.
     *
     * @param baseCurrency The base currency code (e.g., USD, EUR, etc.).
     * @return A Map containing the exchange rates for the given base currency.
     */
    public Map<String, Object> getExchangeRates(String baseCurrency) {
        String url = "https://open.er-api.com/v6/latest/USD?apikey=" + apiKey;

        logger.info("Fetching exchange rates for base currency: {}", baseCurrency);
        logger.debug("API URL: {}", url);

        try {
            // Making the API call to fetch exchange rates
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                logger.error("Received null response from the exchange rate API for base currency: {}", baseCurrency);
                throw new RuntimeException("Null response received from the exchange rate API.");
            }

            logger.info("Exchange rates successfully fetched for base currency: {}", baseCurrency);
            return response;

        } catch (HttpClientErrorException e) {
            logger.error("HTTP error occurred while fetching exchange rates for base currency: {}. Status code: {}", baseCurrency, e.getStatusCode(), e);
            throw new RuntimeException("HTTP error while fetching exchange rates: " + e.getStatusCode(), e);

        } catch (HttpServerErrorException e) {
            logger.error("Server error occurred while fetching exchange rates for base currency: {}. Status code: {}", baseCurrency, e.getStatusCode(), e);
            throw new RuntimeException("Server error while fetching exchange rates: " + e.getStatusCode(), e);

        } catch (Exception e) {
            logger.error("Error fetching exchange rates for base currency: {}", baseCurrency, e);
            throw new RuntimeException("Unexpected error occurred while fetching exchange rates", e);
        }
    }

    /**
     * Retrieves the exchange rate from the base currency to the target currency, using caching to avoid redundant API calls.
     * This method first checks if the exchange rate is cached. If not, it fetches the rate from the external API and caches it.
     *
     * @param baseCurrency   The base currency code (e.g., USD, EUR, etc.).
     * @param targetCurrency The target currency code (e.g., GBP, INR, etc.).
     * @return The exchange rate for conversion from base currency to target currency.
     */
    @Cacheable(value = "exchangeRates", key = "#baseCurrency + '-' + #targetCurrency", unless = "#result == 1.0")
    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        logger.info("Fetching exchange rate from {} to {}", baseCurrency, targetCurrency);

        // Fetch the exchange rates for the base currency
        Map<String, Object> exchangeRates = getExchangeRates(baseCurrency);

        // Check if the rates are available and if the target currency is present in the rates map
        if (exchangeRates != null && exchangeRates.containsKey("rates")) {
            Map<String, Double> rates = (Map<String, Double>) exchangeRates.get("rates");
            if (rates.containsKey(targetCurrency)) {
                logger.debug("Found exchange rate: {} for {} to {}", rates.get(targetCurrency), baseCurrency, targetCurrency);
                return rates.get(targetCurrency);
            }
        }

        // Log a warning and default to a rate of 1.0 if the exchange rate is not found
        logger.warn("Exchange rate for {} to {} not found. Defaulting to 1.", baseCurrency, targetCurrency);
        return 1.0; // Default to 1 if rate is unavailable
    }
}
