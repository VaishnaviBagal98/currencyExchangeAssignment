package com.currencyexchangediscount.assignment.currencyexchangediscount.controller;

import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request.BillRequest;
import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.response.BillResponse;
import com.currencyexchangediscount.assignment.currencyexchangediscount.service.BillService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing billing operations.
 * <p>
 * This controller exposes an API endpoint to calculate a bill based on a request containing
 * currency exchange details and related data. It delegates the bill calculation logic to the
 * {@link BillService} service layer.
 * </p>
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class BillingController {

    // Service to handle bill generation logic
    @Autowired
    private BillService billService;

    /**
     * Endpoint to calculate the bill.
     * <p>
     * Accepts a {@link BillRequest} object in the request body, which contains the necessary data
     * to calculate the bill. It returns a {@link BillResponse} containing the calculated bill.
     * </p>
     *
     * @param billRequest the request body containing bill calculation details
     * @return ResponseEntity containing the bill response or error status
     */
    @PostMapping("/calculate")
    public ResponseEntity<BillResponse> getBill(@RequestBody BillRequest billRequest) {

        // Log the received request for bill calculation
//        log.info("Received bill request with details: {}", billRequest);

        try {
            // Delegate the bill calculation to the service layer
            BillResponse billResponse = billService.generateBill(billRequest);

            // Log the successful bill calculation
//            log.info("Bill calculation successful. Response: {}", billResponse);

            // Return the calculated bill in the response body
            return ResponseEntity.ok(billResponse);
        } catch (Exception e) {
            // Log the error and return an internal server error response
//            log.error("Error occurred while calculating the bill", e);
            return ResponseEntity.status(500).build();
        }
    }
}
