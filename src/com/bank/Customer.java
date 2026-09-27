package com.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a bank customer.
 *
 * Ek customer ke paas multiple bank accounts
 * ho sakte hain.
 */
public class Customer {

    // ==========================================
    // FIELDS
    // ==========================================

    private int customerId;

    private String name;

    private String phone;

    private String email;

    private String address;

    // One customer can have multiple accounts
    private List<Account> accounts;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public Customer(
            int customerId,
            String name,
            String phone,
            String email,
            String address) {

        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;

        // Initialize account list
        this.accounts = new ArrayList<>();
    }


    // ==========================================
    // ADD ACCOUNT
    // ==========================================

    /**
     * Links an account with this customer.
     */
    public void addAccount(Account account) {

        if (account == null) {

            throw new BankException(
                    "Account cannot be null!"
            );
        }

        accounts.add(account);
    }


    // ==========================================
    // GET ACCOUNTS
    // ==========================================

    /**
     * Returns a read-only view of customer accounts.
     *
     * Outside code cannot directly modify
     * the internal accounts list.
     */
    public List<Account> getAccounts() {

        return Collections.unmodifiableList(
                accounts
        );
    }


    // ==========================================
    // GETTERS
    // ==========================================

    public int getCustomerId() {

        return customerId;
    }

    public String getName() {

        return name;
    }

    public String getPhone() {

        return phone;
    }

    public String getEmail() {

        return email;
    }

    public String getAddress() {

        return address;
    }
}