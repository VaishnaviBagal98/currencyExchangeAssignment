package com.currencyexchangediscount.assignment.currencyexchangediscount.service;

import com.currencyexchangediscount.assignment.currencyexchangediscount.dto.request.BillRequest;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class DiscountService {

    public double calculateDiscountPercentage(String userType, Float tenure) {
        switch (userType.toUpperCase()) {
            case "EMPLOYEE":
                log.debug("User is an employee. Applying 30% discount.");
                return 0.30;
            case "AFFILIATE":
                log.debug("User is an affiliate. Applying 10% discount.");
                return 0.10;
        }
        if (tenure > 2) {
            log.debug("User is a loyal customer (over 2 years). Applying 5% discount.");
            return 0.05;
        }

        log.debug("No user based discount");
        return 0;
    }
}
