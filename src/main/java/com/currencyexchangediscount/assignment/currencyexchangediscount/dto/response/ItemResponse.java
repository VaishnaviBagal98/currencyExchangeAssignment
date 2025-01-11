package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {

    private String category;
    private Double originalCurrencyAmount;
    private Double originalCurrencyDiscount;
    private Double quantity;
    private String description;
    @JsonIgnore
    private Double conversionRate;

    @JsonProperty("originalCurrencyTotal")
    private Double getOriginalCurrencyTotal() {
        return (originalCurrencyAmount - originalCurrencyDiscount) * quantity;
    }

    @JsonProperty("targetCurrencyAmount")
    private Double getTargetCurrencyAmount() {
        return originalCurrencyAmount * conversionRate;
    }

    @JsonProperty("targetCurrencyDiscount")
    private Double getTargetCurrencyDiscount() {
        return originalCurrencyDiscount * conversionRate;
    }

    @JsonProperty("targetCurrencyTotal")
    private Double getTargetCurrencyTotal(){
        return getOriginalCurrencyAmount() * conversionRate;
    }
}
