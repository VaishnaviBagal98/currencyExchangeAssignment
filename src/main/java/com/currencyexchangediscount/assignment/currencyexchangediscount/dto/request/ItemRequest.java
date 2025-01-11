package com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    private String category;
    private Double amount;
    private Double quantity;
    private String description;

    public Double getTotalAmount(){
       return  quantity * amount ;
    }
}
