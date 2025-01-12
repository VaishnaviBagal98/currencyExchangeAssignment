package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Service responsible for fetching exchange rates for currency conversion.
 * <p>
 * This service provides methods to retrieve the exchange rates from an external API and cache them for performance optimization.
 * It can fetch the conversion rate between two currencies and store them in the cache to avoid redundant API calls.
 * </p>
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@Service
public class CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);

    private final RestTemplate restTemplate;

    @Value("${exchange.api.url}")
    private String apiUrl;

    @Value("${exchange.api.key}")
    private String apiKey;

    @Autowired
    private CacheManager cacheManager;

    /**
     * Constructor to initialize the CurrencyExchangeService.
     * <p>
     * Creates a new instance of RestTemplate to make HTTP requests.
     * </p>
     */
    public CurrencyExchangeService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Retrieves the exchange rates for a given base currency from an external API.
     * <p>
     * This method fetches exchange rates for the given base currency from an external API and returns the data in a Map format.
     * </p>
     *
     * @param baseCurrency The base currency code (e.g., USD, EUR, etc.).
     * @return A Map containing the exchange rates for the given base currency.
     */
    public Map<String, Object> getExchangeRates(String baseCurrency) {
        // Hardcoded API URL and key for the external exchange service
        apiUrl = "https://open.er-api.com/v6";
        apiKey = "b42152b3c8054b014b0fe388";

        // API URL to fetch the exchange rates
        String url = "https://open.er-api.com/v6/latest/USD?apikey=b42152b3c8054b014b0fe388";

        logger.info("Fetching exchange rates for base currency: {}", baseCurrency);
        logger.debug("API URL: {}", url);

        try {
            // Making the API call to fetch exchange rates
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            logger.info("Exchange rates successfully fetched for base currency: {}", baseCurrency);
            return response;
        } catch (Exception e) {
            logger.error("Error fetching exchange rates for base currency: {}", baseCurrency, e);
            throw e;
        }
    }

    /**
     * Retrieves the exchange rate from the base currency to the target currency, using caching to avoid redundant API calls.
     * <p>
     * This method first checks if the exchange rate is cached. If not, it fetches the rate from the external API and caches it.
     * </p>
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
