package com.currencyexchangediscount.assignment.currencyexchangediscount.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class for setting up caching mechanisms.
 * <p>
 * This class enables caching within the Spring application and sets up a {@link CacheManager}
 * bean using Caffeine, a high-performance, Java-based caching library.
 * </p>
 * The cache manager will be used throughout the application to manage cache for various services.
 * Caching is enabled globally through the {@code @EnableCaching} annotation.
 * @author Vaishnavi Bagal
 * @version 1.0
 */
@Configuration
@EnableCaching
public class CacheConfig {

    // Logger for logging relevant events or issues
    private static final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    /**
     * Configures and returns a {@link CacheManager} bean for the application.
     * <p>
     * This method creates a CaffeineCacheManager which is a part of the Caffeine caching library.
     * Caffeine offers an efficient and powerful caching mechanism, and it can be used for
     * various caching strategies within the application.
     * </p>
     *
     * @return a {@link CacheManager} instance configured with Caffeine
     */
    @Bean
    public CacheManager cacheManager() {
        // Log the cache manager initialization
        logger.info("Initializing Caffeine Cache Manager");

        // Create and return a new CaffeineCacheManager
        CacheManager cacheManager = new CaffeineCacheManager();

        // Log the successful creation of the cache manager
        logger.info("Caffeine Cache Manager initialized successfully");

        return cacheManager;
    }
}
