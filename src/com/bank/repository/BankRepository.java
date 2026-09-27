package com.bank.repository;

import com.bank.Account;
import com.bank.Customer;
import com.bank.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository layer.
 *
 * Abhi hum database use nahi kar rahe.
 * Isliye data ArrayList me memory ke andar store hoga.
 *
 * Future me isi layer ko JDBC / MySQL ke saath
 * connect kar sakte hain.
 */
public class BankRepository {

    // ==========================================
    // IN-MEMORY DATA STORAGE
    // ==========================================

    private List<Customer> customers =
            new ArrayList<>();

    private List<Account> accounts =
            new ArrayList<>();

    private List<Transaction> transactions =
            new ArrayList<>();


    // ==========================================
    // CUSTOMER OPERATIONS
    // ==========================================

    /**
     * Save a customer.
     */
    public void saveCustomer(Customer customer) {

        customers.add(customer);
    }


    /**
     * Find customer using customer ID.
     *
     * @return Customer if found, otherwise null
     */
    public Customer findCustomerById(
            int customerId) {

        for (Customer customer : customers) {

            if (customer.getCustomerId()
                    == customerId) {

                return customer;
            }
        }

        return null;
    }


    // ==========================================
    // ACCOUNT OPERATIONS
    // ==========================================

    /**
     * Save an account.
     */
    public void saveAccount(Account account) {

        accounts.add(account);
    }


    /**
     * Find account using account number.
     *
     * @return Account if found, otherwise null
     */
    public Account findAccountByNumber(
            long accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber()
                    == accountNumber) {

                return account;
            }
        }

        return null;
    }


    // ==========================================
    // TRANSACTION OPERATIONS
    // ==========================================

    /**
     * Save a transaction.
     */
    public void saveTransaction(
            Transaction transaction) {

        transactions.add(transaction);
    }


    /**
     * Get all transactions belonging
     * to a particular account.
     */
    public List<Transaction>
    getTransactionsByAccount(
            long accountNumber) {

        List<Transaction> result =
                new ArrayList<>();

        for (Transaction transaction :
                transactions) {

            if (transaction.getAccountNumber()
                    == accountNumber) {

                result.add(transaction);
            }
        }

        return result;
    }
}