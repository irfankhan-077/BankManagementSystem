package com.bank;

import java.math.BigDecimal;

/**
 * Represents a Savings Account.
 *
 * Savings account me account balance se
 * zyada amount withdraw nahi kiya ja sakta.
 */
public class SavingsAccount extends Account {

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public SavingsAccount(
            long accountNumber,
            BigDecimal balance,
            AccountStatus status) {

        // Parent class constructor call
        super(
                accountNumber,
                balance,
                status
        );
    }


    // ==========================================
    // ACCOUNT TYPE
    // ==========================================

    @Override
    public AccountType getAccountType() {

        return AccountType.SAVINGS;
    }


    // ==========================================
    // WITHDRAW
    // ==========================================

    @Override
    public void withdraw(
            BigDecimal amount) {

        // Common amount validation
        validateAmount(amount);

        // Savings account overdraft allow nahi karta
        if (amount.compareTo(getBalance()) > 0) {

            throw new BankException(
                    "Insufficient balance!"
            );
        }

        // Reduce balance
        decreaseBalance(amount);
    }
}