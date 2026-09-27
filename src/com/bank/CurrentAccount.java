package com.bank;

import java.math.BigDecimal;

/**
 * Represents a Current Account.
 *
 * Current account me overdraft facility available hai.
 */
public class CurrentAccount extends Account {

    // ==========================================
    // OVERDRAFT LIMIT
    // ==========================================

    private BigDecimal overdraftLimit =
            new BigDecimal("50000");


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public CurrentAccount(
            long accountNumber,
            BigDecimal balance,
            AccountStatus status) {

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

        return AccountType.CURRENT;
    }


    // ==========================================
    // WITHDRAW
    // ==========================================

    @Override
    public void withdraw(
            BigDecimal amount) {

        // Common amount validation
        validateAmount(amount);

        /*
         * Current Account overdraft allow karta hai.
         *
         * Example:
         * Balance = ₹20,000
         * Overdraft = ₹50,000
         *
         * Total available = ₹70,000
         */
        BigDecimal availableAmount =
                getBalance().add(overdraftLimit);

        // Check overdraft limit
        if (amount.compareTo(
                availableAmount) > 0) {

            throw new BankException(
                    "Overdraft limit exceeded!"
            );
        }

        // Reduce balance
        decreaseBalance(amount);
    }
}