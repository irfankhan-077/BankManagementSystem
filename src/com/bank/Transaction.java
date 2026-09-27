package com.bank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents one transaction performed on a bank account.
 *
 * Transaction object sirf transaction ki information
 * store karta hai.
 */
public class Transaction {

    // ==========================================
    // FIELDS
    // ==========================================

    private String transactionId;

    private long accountNumber;

    private TransactionType type;

    private BigDecimal amount;

    private LocalDateTime date;

    private String description;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Transaction(
            String transactionId,
            long accountNumber,
            TransactionType type,
            BigDecimal amount,
            LocalDateTime date,
            String description) {

        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public String getTransactionId() {
        return transactionId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}