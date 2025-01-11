package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BillResponse {


    private List<ItemResponse> itemResponseList;
    private Double originalCurrencyTotal;

    @JsonProperty("targetCurrencyTotal")
    private Double getTargetCurrencyTotal(){
        return  originalCurrencyTotal * conversionRate;
    }
    private Double originalCurrencyTotalDiscount;

    @JsonProperty("targetCurrencyTotalDiscount")
    private Double getTargetCurrencyTotalDiscount(){
        return originalCurrencyTotalDiscount * conversionRate;
    }
    private Double originalCurrencyBillDiscount;

    @JsonProperty("targetCurrencyBillDiscount")
    private Double getTargetCurrencyBillDiscount(){
        return originalCurrencyBillDiscount * conversionRate;
    }
    private Double conversionRate;

    @JsonProperty("targetCurrencyTotalPercentageDiscount")
    private Double getTargetCurrencyTotalPercentageDiscount(){
        return originalCurrencyTotalPercentageDiscount * conversionRate;
    }
    private Double originalCurrencyTotalPercentageDiscount;


}
