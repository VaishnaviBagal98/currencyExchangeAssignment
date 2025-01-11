package com.currencyexchangediscount.assignment.currencyexchangediscount.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);

    private final RestTemplate restTemplate;
    @Value("${exchange.api.url}")
    private String apiUrl;
    @Value("${exchange.api.key}")
    private String apiKey;

    public CurrencyExchangeService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> getExchangeRates(String baseCurrency) {
        String url = String.format("%s/latest/%s?apikey=%s", apiUrl, baseCurrency, apiKey);

        logger.info("Fetching exchange rates for base currency: {}", baseCurrency);
        logger.debug("API URL: {}", url);

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            logger.info("Exchange rates successfully fetched for base currency: {}", baseCurrency);
            return response;
        } catch (Exception e) {
            logger.error("Error fetching exchange rates for base currency: {}", baseCurrency, e);
            throw e;
        }
    }


    /**
     * Retrieves the exchange rate from the base currency to the target currency.
     *
     * @param baseCurrency   The base currency code.
     * @param targetCurrency The target currency code.
     * @return The exchange rate for conversion.
     */
    public double getExchangeRate(String baseCurrency, String targetCurrency) {
        logger.info("Fetching exchange rate from {} to {}", baseCurrency, targetCurrency);

        Map<String, Object> exchangeRates = getExchangeRates(baseCurrency);

        if (exchangeRates != null && exchangeRates.containsKey("conversion_rates")) {
            Map<String, Double> rates = (Map<String, Double>) exchangeRates.get("conversion_rates");
            if (rates.containsKey(targetCurrency)) {
                logger.debug("Found exchange rate: {} for {} to {}", rates.get(targetCurrency), baseCurrency, targetCurrency);
                return rates.get(targetCurrency);
            }
        }

        logger.warn("Exchange rate for {} to {} not found. Defaulting to 1.", baseCurrency, targetCurrency);
        return 1.0; // Default to 1 if rate is unavailable
    }

}
