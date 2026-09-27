package com.bank;

import java.math.BigDecimal;

/**
 * Abstract base class for all bank accounts.
 *
 * SavingsAccount aur CurrentAccount
 * is class ko extend karenge.
 */
public abstract class Account {

    // ==========================================
    // FIELDS
    // ==========================================

    private long accountNumber;

    private BigDecimal balance;

    private AccountStatus status;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Account(
            long accountNumber,
            BigDecimal balance,
            AccountStatus status) {

        // Validate account number
        if (accountNumber <= 0) {

            throw new BankException(
                    "Account number must be greater than 0"
            );
        }

        // Validate initial balance
        if (balance == null ||
                balance.compareTo(BigDecimal.ZERO) < 0) {

            throw new BankException(
                    "Initial balance cannot be negative"
            );
        }

        // Validate status
        if (status == null) {

            throw new BankException(
                    "Account status cannot be null"
            );
        }

        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
    }


    // ==========================================
    // AMOUNT VALIDATION
    // ==========================================

    protected void validateAmount(
            BigDecimal amount) {

        if (amount == null ||
                amount.compareTo(
                        BigDecimal.ZERO) <= 0) {

            throw new BankException(
                    "Amount must be greater than 0"
            );
        }
    }


    // ==========================================
    // DEPOSIT
    // ==========================================

    public void deposit(
            BigDecimal amount) {

        validateAmount(amount);

        increaseBalance(amount);
    }


    // ==========================================
    // WITHDRAW
    // ==========================================

    /*
     * Withdrawal ka actual rule child classes
     * define karengi.
     *
     * SavingsAccount:
     * → insufficient balance check
     *
     * CurrentAccount:
     * → overdraft check
     */
    public abstract void withdraw(
            BigDecimal amount);


    // ==========================================
    // ACCOUNT TYPE
    // ==========================================

    /*
     * Child class batayegi ki account
     * SAVINGS hai ya CURRENT.
     */
    public abstract AccountType getAccountType();


    // ==========================================
    // GETTERS
    // ==========================================

    public long getAccountNumber() {

        return accountNumber;
    }

    public BigDecimal getBalance() {

        return balance;
    }

    public AccountStatus getStatus() {

        return status;
    }


    // ==========================================
    // INTERNAL BALANCE OPERATIONS
    // ==========================================

    /*
     * Balance ko directly public nahi karna.
     *
     * Ye methods child classes ke liye protected hain.
     */

    protected void increaseBalance(
            BigDecimal amount) {

        balance = balance.add(amount);
    }

    protected void decreaseBalance(
            BigDecimal amount) {

        balance = balance.subtract(amount);
    }


    // ==========================================
    // CHANGE ACCOUNT STATUS
    // ==========================================

    public void setStatus(
            AccountStatus status) {

        if (status == null) {

            throw new BankException(
                    "Account status cannot be null"
            );
        }

        this.status = status;
    }
}