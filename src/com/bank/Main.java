package com.bank;

/**
 * Entry point of the Bank Management System.
 *
 * Main class ka kaam sirf application start karna hai.
 * Actual menu aur business logic yahan nahi rakhenge.
 */
public class Main {

    public static void main(String[] args) {

        // Create the application
        BankApplication application =
                new BankApplication();

        // Start the application
        application.start();
    }
}