package com.bank;

/**
 * Custom exception for Bank Management System.
 *
 * Jab banking operation me koi invalid situation aaye,
 * hum BankException throw karenge.
 */
public class BankException extends RuntimeException {

    public BankException(String message) {

        super(message);
    }
}