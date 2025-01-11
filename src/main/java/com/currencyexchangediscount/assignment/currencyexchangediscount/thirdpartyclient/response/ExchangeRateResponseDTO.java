package com.currencyexchangediscount.assignment.currencyexchangediscount.thirdpartyclient.response;

import lombok.Data;

import java.util.Map;

@Data
public class ExchangeRateResponseDTO {
    private String result;
    private String documentation;
    private String termsOfUse;
    private long timeLastUpdateUnix;
    private String timeLastUpdateUtc;
    private long timeNextUpdateUnix;
    private String timeNextUpdateUtc;
    private String baseCode;
    private Map<String, Double> conversionRates;
}

