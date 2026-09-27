package com.bank.service;

import com.bank.Account;
import com.bank.AccountStatus;
import com.bank.BankException;
import com.bank.AccountType;
import com.bank.CurrentAccount;
import com.bank.Customer;
import com.bank.SavingsAccount;
import com.bank.Transaction;
import com.bank.TransactionType;
import com.bank.repository.BankRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service layer of the Bank Management System.
 *
 * Service layer contains the main business logic.
 *
 * Flow:
 *
 * BankApplication
 *       ↓
 * BankService
 *       ↓
 * BankRepository
 */
public class BankService {

    // ==========================================
    // FIELDS
    // ==========================================

    private BankRepository repository;

    private int nextCustomerId = 1001;

    private long nextAccountNumber = 1000000001L;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public BankService() {

        repository = new BankRepository();
    }


    // ==========================================
    // ID GENERATION
    // ==========================================

    private int generateCustomerId() {

        return nextCustomerId++;
    }


    public long generateAccountNumber() {

        return nextAccountNumber++;
    }


    // ==========================================
    // CUSTOMER OPERATIONS
    // ==========================================

    /**
     * Creates and saves a new customer.
     */
    public Customer createCustomer(
            String name,
            String phone,
            String email,
            String address) {

        int customerId =
                generateCustomerId();

        Customer customer =
                new Customer(
                        customerId,
                        name,
                        phone,
                        email,
                        address
                );

        repository.saveCustomer(customer);

        return customer;
    }


    /**
     * Finds a customer by ID.
     */
    public Customer findCustomer(
            int customerId) {

        return repository.findCustomerById(
                customerId
        );
    }


    /**
     * Gets a customer or throws an exception
     * if the customer does not exist.
     */
    public Customer getCustomer(
            int customerId) {

        Customer customer =
                findCustomer(customerId);

        if (customer == null) {

            throw new BankException(
                    "Customer not found!"
            );
        }

        return customer;
    }


    // ==========================================
    // ACCOUNT OPERATIONS
    // ==========================================

    /**
     * Creates and saves an account.
     *
     * Account object is already created by
     * BankApplication. Service validates that
     * the account number is unique.
     */
    public void createAccount(
            Account account) {

        if (account == null) {

            throw new BankException(
                    "Account cannot be null!"
            );
        }

        if (findAccount(
                account.getAccountNumber()) != null) {

            throw new BankException(
                    "Account already exists!"
            );
        }

        repository.saveAccount(account);
    }


    /**
     * Finds an account by account number.
     */
    public Account findAccount(
            long accountNumber) {

        return repository.findAccountByNumber(
                accountNumber
        );
    }


    /**
     * Returns an account only if it exists
     * and is currently active.
     */
    private Account getActiveAccount(
            long accountNumber) {

        Account account =
                findAccount(accountNumber);

        if (account == null) {

            throw new BankException(
                    "Account not found!"
            );
        }

        if (account.getStatus()
                != AccountStatus.ACTIVE) {

            throw new BankException(
                    "Account is not active!"
            );
        }

        return account;
    }


    // ==========================================
    // DEPOSIT
    // ==========================================

    public void deposit(
            long accountNumber,
            BigDecimal amount) {

        Account account =
                getActiveAccount(accountNumber);

        /*
         * Account.deposit() already validates
         * that amount is greater than zero.
         */
        account.deposit(amount);

        Transaction transaction =
                new Transaction(
                        generateTransactionId(),
                        accountNumber,
                        TransactionType.DEPOSIT,
                        amount,
                        LocalDateTime.now(),
                        "Money deposited"
                );

        repository.saveTransaction(
                transaction
        );
    }


    // ==========================================
    // WITHDRAW
    // ==========================================

    public void withdraw(
            long accountNumber,
            BigDecimal amount) {

        Account account =
                getActiveAccount(accountNumber);

        /*
         * SavingsAccount / CurrentAccount
         * handle withdrawal rules.
         */
        account.withdraw(amount);

        Transaction transaction =
                new Transaction(
                        generateTransactionId(),
                        accountNumber,
                        TransactionType.WITHDRAW,
                        amount,
                        LocalDateTime.now(),
                        "Money withdrawn"
                );

        repository.saveTransaction(
                transaction
        );
    }


    // ==========================================
    // TRANSFER
    // ==========================================

    public void transfer(
            long fromAccountNumber,
            long toAccountNumber,
            BigDecimal amount) {

        // Same account transfer is not allowed
        if (fromAccountNumber ==
                toAccountNumber) {

            throw new BankException(
                    "Cannot transfer to same account!"
            );
        }

        // Validate transfer amount
        if (amount == null ||
                amount.compareTo(
                        BigDecimal.ZERO) <= 0) {

            throw new BankException(
                    "Transfer amount must be greater than 0"
            );
        }

        // Get both active accounts
        Account sender =
                getActiveAccount(
                        fromAccountNumber
                );

        Account receiver =
                getActiveAccount(
                        toAccountNumber
                );


        // ======================================
        // MONEY MOVEMENT
        // ======================================

        sender.withdraw(amount);

        receiver.deposit(amount);


        // ======================================
        // SENDER TRANSACTION
        // ======================================

        Transaction senderTransaction =
                new Transaction(
                        generateTransactionId(),
                        fromAccountNumber,
                        TransactionType.TRANSFER_SENT,
                        amount,
                        LocalDateTime.now(),
                        "Transfer to account "
                                + toAccountNumber
                );

        repository.saveTransaction(
                senderTransaction
        );


        // ======================================
        // RECEIVER TRANSACTION
        // ======================================

        Transaction receiverTransaction =
                new Transaction(
                        generateTransactionId(),
                        toAccountNumber,
                        TransactionType.TRANSFER_RECEIVED,
                        amount,
                        LocalDateTime.now(),
                        "Transfer from account "
                                + fromAccountNumber
                );

        repository.saveTransaction(
                receiverTransaction
        );
    }


    // ==========================================
    // TRANSACTION ID
    // ==========================================

    private String generateTransactionId() {

        return "TXN-" +
                UUID.randomUUID();
    }


    // ==========================================
    // TRANSACTION HISTORY
    // ==========================================

    public List<Transaction>
    getTransactionHistory(
            long accountNumber) {

        // First make sure account exists
        if (findAccount(accountNumber)
                == null) {

            throw new BankException(
                    "Account not found!"
            );
        }

        return repository
                .getTransactionsByAccount(
                        accountNumber
                );
    }


    // ==========================================
    // BLOCK ACCOUNT
    // ==========================================

    public void blockAccount(
            long accountNumber) {

        Account account =
                findAccount(accountNumber);

        if (account == null) {

            throw new BankException(
                    "Account not found!"
            );
        }

        if (account.getStatus()
                == AccountStatus.BLOCKED) {

            throw new BankException(
                    "Account is already blocked!"
            );
        }

        account.setStatus(
                AccountStatus.BLOCKED
        );
    }


    // ==========================================
    // UNBLOCK ACCOUNT
    // ==========================================

    public void unblockAccount(
            long accountNumber) {

        Account account =
                findAccount(accountNumber);

        if (account == null) {

            throw new BankException(
                    "Account not found!"
            );
        }

        if (account.getStatus()
                == AccountStatus.ACTIVE) {

            throw new BankException(
                    "Account is already active!"
            );
        }

        account.setStatus(
                AccountStatus.ACTIVE
        );
    }
}