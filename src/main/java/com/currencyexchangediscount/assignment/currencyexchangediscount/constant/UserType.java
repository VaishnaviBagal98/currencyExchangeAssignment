package com.currencyexchangediscount.assignment.currencyexchangediscount.constant;

public enum UserType {
    EMPLOYEE(0.30, "User is an employee. Applying 30% discount."),
    AFFILIATE(0.10, "User is an affiliate. Applying 10% discount.");

    private final double discountRate;
    private final String logMessage;

    UserType(double discountRate, String logMessage) {
        this.discountRate = discountRate;
        this.logMessage = logMessage;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public String getLogMessage() {
        return logMessage;
    }
}
