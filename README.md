# Currency Exchange and Discount Service

This is a Spring Boot application that provides functionality for calculating bills with currency exchange and discounts applied based on user type and tenure. The service generates bills by converting amounts from one currency to another and applying appropriate discounts on items based on the user's type (e.g., employee, affiliate) and tenure with the company.

## Features

- **Bill Calculation**: Generates a detailed bill with itemized amounts, discounts, and total amounts in both original and target currencies.
- **Currency Conversion**: Fetches live exchange rates from an external API (ER API) to convert bill amounts from the original currency to the target currency.
- **Discount Calculation**: Applies discounts based on user type and tenure.
- **Caching**: Utilizes caching to store exchange rates for efficient retrieval.

## Tech Stack

- **Spring Boot**: The main framework for building the application.
- **JUnit 5**: For unit and integration testing.
- **Mockito**: For mocking dependencies in tests.
- **Caffeine Cache**: For caching the exchange rates.
- **H2 Database**: For simple in-memory database usage.

## Prerequisites

Before you start, you need to have the following installed:

- **Java 11 or higher**: The application is built with Java 11.
- **Maven**: For building the application.
- **Postman or CURL** (optional): To test the API endpoints.

## Setup and Installation

1. Clone the repository to your local machine:

    ```bash
    git clone https://github.com/yourusername/currency-exchange-discount.git
    cd currency-exchange-discount
    ```

2. Build the project using Maven:

    ```bash
    mvn clean install
    ```

3. Run the application:

    ```bash
    mvn spring-boot:run
    ```

   This will start the application on `localhost:8080` by default.

## Configuration

- **API Key**: The application uses a public API for currency exchange (ER API). You can get your own API key if needed and configure it in `application.properties`.
  
- **Database Configuration**: The application uses an in-memory H2 database by default. You can modify the configuration to use a different database in the `application.properties` file.

  ```properties
  spring.datasource.url=jdbc:h2:mem:testdb
  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.username=sa
  spring.datasource.password=password
  spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
