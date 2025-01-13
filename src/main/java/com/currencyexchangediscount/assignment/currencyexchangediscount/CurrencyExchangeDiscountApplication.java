package com.currencyexchangediscount.assignment.currencyexchangediscount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Currency Exchange Discount application.
 * <p>
 * This is the main class responsible for bootstrapping the Spring Boot application. It contains the main method,
 * which is the starting point of the application. The application is auto-configured using the {@link SpringBootApplication}
 * annotation, which combines several useful annotations such as {@link EnableAutoConfiguration}, {@link ComponentScan},
 * and {@link Configuration} for quick setup.
 * </p>
 *
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@SpringBootApplication
@Slf4j
public class CurrencyExchangeDiscountApplication {

    /**
     * The main method that runs the Spring Boot application.
     * <p>
     * This method initializes the Spring Boot application context and starts the embedded web server.
     * </p>
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        log.info("Starting the Currency Exchange Discount Application...");

        // Run the Spring Boot application
        SpringApplication.run(CurrencyExchangeDiscountApplication.class, args);

        log.info("Currency Exchange Discount Application started successfully.");
    }
}
