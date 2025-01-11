package com.currencyexchangediscount.assignment.currencyexchangediscount.controller;


import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request.BillRequest;
import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response.BillResponse;
import com.currencyexchangediscount.assignment.currencyexchangediscount.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<BillResponse> getBill(BillRequest billRequest){
        return  ResponseEntity.ok(billService.generateBill(billRequest));
    }
}
