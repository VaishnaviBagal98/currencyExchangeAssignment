package com.currencyexchangediscount.assignment.currencyexchangediscount;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This is a test class for the CurrencyexchangediscountApplication class.
 * The contextLoads method is used to ensure that the Spring application context is loaded successfully.
 * This is a typical "sanity check" to verify that the Spring Boot application starts correctly and all beans are properly wired.
 */
@SpringBootTest
class CurrencyexchangediscountApplicationTests {

    // Creating a Logger instance to log details during test execution
    private static final Logger logger = LoggerFactory.getLogger(CurrencyexchangediscountApplicationTests.class);

    /**
     * This method is a simple test to check if the Spring context loads without issues.
     * <p>
     * The method does not contain any logic because the purpose is solely to verify that
     * the application context can start up without any exceptions or errors.
     * If the context fails to load, an exception will be thrown, and the test will fail.
     * <p>
     * We can consider this a "smoke test" for the Spring Boot application.
     */
    @Test
    void contextLoads() {
        // Logging the purpose of this test to make it clear during test execution
        logger.info("Executing the contextLoads test to verify the Spring Boot application context loads successfully.");

        // Note: The method is intentionally empty. If the Spring Boot application context fails to load,
        //       this will result in a test failure.
        // If the application context starts successfully, this test will pass without any further action required.
	}
}
