package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    private Double totalAmount;
    private String userType;
    private Float customerTenure;
    private String originalCurrency;
    private String targetCurrency;
    private List<ItemRequest> itemRequestList;

}
