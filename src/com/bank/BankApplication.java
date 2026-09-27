package com.bank;

import com.bank.service.BankService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Handles user interaction for the Bank Management System.
 *
 * Responsibilities:
 * - Show menu
 * - Take input from user
 * - Display output
 * - Call BankService
 *
 * Business logic is NOT written here.
 */
public class BankApplication {

    private BankService bankService;
    private Scanner scanner;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public BankApplication() {

        bankService = new BankService();
        scanner = new Scanner(System.in);
    }

    // ==========================================
    // START APPLICATION
    // ==========================================

    public void start() {

        boolean running = true;

        while (running) {

            showMenu();

            int choice =
                    readInt("Enter your choice: ");

            try {

                switch (choice) {

                    case 1:
                        createCustomer();
                        break;

                    case 2:
                        createAccount();
                        break;

                    case 3:
                        showCustomerDetails();
                        break;

                    case 4:
                        deposit();
                        break;

                    case 5:
                        withdraw();
                        break;

                    case 6:
                        transfer();
                        break;

                    case 7:
                        checkBalance();
                        break;

                    case 8:
                        transactionHistory();
                        break;

                    case 9:
                        blockAccount();
                        break;

                    case 10:
                        unblockAccount();
                        break;

                    case 11:
                        running = false;

                        System.out.println(
                                "\nThank you for using " +
                                        "Bank Management System!"
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid choice!"
                        );
                }

            } catch (BankException e) {

                System.out.println(
                        "Error: " + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    // ==========================================
    // MENU
    // ==========================================

    private void showMenu() {

        System.out.println(
                "\n=========================================="
        );

        System.out.println(
                "       BANK MANAGEMENT SYSTEM"
        );

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "1. Create Customer"
        );

        System.out.println(
                "2. Create Account"
        );

        System.out.println(
                "3. View Customer Details"
        );

        System.out.println(
                "4. Deposit"
        );

        System.out.println(
                "5. Withdraw"
        );

        System.out.println(
                "6. Transfer"
        );

        System.out.println(
                "7. Check Balance"
        );

        System.out.println(
                "8. Transaction History"
        );

        System.out.println(
                "9. Block Account"
        );

        System.out.println(
                "10. Unblock Account"
        );

        System.out.println(
                "11. Exit"
        );

        System.out.println(
                "=========================================="
        );
    }

    // ==========================================
    // CREATE CUSTOMER
    // ==========================================

    private void createCustomer() {

        System.out.println(
                "\n========== CREATE CUSTOMER =========="
        );

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        if (name.isBlank()) {

            throw new BankException(
                    "Name cannot be empty!"
            );
        }

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Customer customer =
                bankService.createCustomer(
                        name,
                        phone,
                        email,
                        address
                );

        System.out.println(
                "\nCustomer created successfully!"
        );

        System.out.println(
                "Customer ID: "
                        + customer.getCustomerId()
        );
    }

    // ==========================================
    // CREATE ACCOUNT
    // ==========================================

    private void createAccount() {

        System.out.println(
                "\n========== CREATE ACCOUNT =========="
        );

        int customerId =
                readInt("Enter customer ID: ");

        Customer customer =
                bankService.findCustomer(
                        customerId
                );

        if (customer == null) {

            throw new BankException(
                    "Customer not found!"
            );
        }

        System.out.println(
                "1. Savings Account"
        );

        System.out.println(
                "2. Current Account"
        );

        int type =
                readInt("Select account type: ");

        BigDecimal initialBalance =
                readAmount(
                        "Enter initial balance: "
                );

        long accountNumber =
                bankService.generateAccountNumber();

        Account account;

        if (type == 1) {

            account =
                    new SavingsAccount(
                            accountNumber,
                            initialBalance,
                            AccountStatus.ACTIVE
                    );

        } else if (type == 2) {

            account =
                    new CurrentAccount(
                            accountNumber,
                            initialBalance,
                            AccountStatus.ACTIVE
                    );

        } else {

            throw new BankException(
                    "Invalid account type!"
            );
        }

        // Link account with customer
        customer.addAccount(account);

        // Save account
        bankService.createAccount(account);

        System.out.println(
                "\nAccount created successfully!"
        );

        System.out.println(
                "Account Number: "
                        + accountNumber
        );

        System.out.println(
                "Account Type: "
                        + account.getAccountType()
        );
    }

    // ==========================================
    // DEPOSIT
    // ==========================================

    private void deposit() {

        System.out.println(
                "\n========== DEPOSIT =========="
        );

        long accountNumber =
                readLong(
                        "Enter account number: "
                );

        BigDecimal amount =
                readAmount(
                        "Enter amount: "
                );

        bankService.deposit(
                accountNumber,
                amount
        );

        System.out.println(
                "Deposit successful!"
        );
    }

    // ==========================================
    // WITHDRAW
    // ==========================================

    private void withdraw() {

        System.out.println(
                "\n========== WITHDRAW =========="
        );

        long accountNumber =
                readLong(
                        "Enter account number: "
                );

        BigDecimal amount =
                readAmount(
                        "Enter amount: "
                );

        bankService.withdraw(
                accountNumber,
                amount
        );

        System.out.println(
                "Withdrawal successful!"
        );
    }

    // ==========================================
    // TRANSFER
    // ==========================================

    private void transfer() {

        System.out.println(
                "\n========== TRANSFER =========="
        );

        long fromAccount =
                readLong(
                        "From account: "
                );

        long toAccount =
                readLong(
                        "To account: "
                );

        BigDecimal amount =
                readAmount(
                        "Enter amount: "
                );

        bankService.transfer(
                fromAccount,
                toAccount,
                amount
        );

        System.out.println(
                "Transfer successful!"
        );
    }

    // ==========================================
    // CHECK BALANCE
    // ==========================================

    private void checkBalance() {

        System.out.println(
                "\n========== CHECK BALANCE =========="
        );

        long accountNumber =
                readLong(
                        "Enter account number: "
                );

        Account account =
                bankService.findAccount(
                        accountNumber
                );

        if (account == null) {

            throw new BankException(
                    "Account not found!"
            );
        }

        System.out.println(
                "Account Number: "
                        + account.getAccountNumber()
        );

        System.out.println(
                "Account Type: "
                        + account.getAccountType()
        );

        System.out.println(
                "Balance: ₹"
                        + account.getBalance()
        );

        System.out.println(
                "Status: "
                        + account.getStatus()
        );
    }

    // ==========================================
    // TRANSACTION HISTORY
    // ==========================================

    private void transactionHistory() {

        System.out.println(
                "\n========== TRANSACTION HISTORY =========="
        );

        long accountNumber =
                readLong(
                        "Enter account number: "
                );

        List<Transaction> transactions =
                bankService.getTransactionHistory(
                        accountNumber
                );

        if (transactions.isEmpty()) {

            System.out.println(
                    "No transaction found."
            );

            return;
        }

        for (Transaction transaction :
                transactions) {

            System.out.println(
                    transaction.getTransactionId()
                            + " | "
                            + transaction.getType()
                            + " | ₹"
                            + transaction.getAmount()
                            + " | "
                            + transaction.getDate()
                            + " | "
                            + transaction.getDescription()
            );
        }
    }

    // ==========================================
    // BLOCK ACCOUNT
    // ==========================================

    private void blockAccount() {

        System.out.println(
                "\n========== BLOCK ACCOUNT =========="
        );

        long accountNumber =
                readLong(
                        "Enter account number: "
                );

        bankService.blockAccount(
                accountNumber
        );

        System.out.println(
                "Account blocked successfully!"
        );
    }

    // ==========================================
    // UNBLOCK ACCOUNT
    // ==========================================

    private void unblockAccount() {

        System.out.println(
                "\n========== UNBLOCK ACCOUNT =========="
        );

        long accountNumber =
                readLong(
                        "Enter account number: "
                );

        bankService.unblockAccount(
                accountNumber
        );

        System.out.println(
                "Account unblocked successfully!"
        );
    }

    // ==========================================
    // CUSTOMER DETAILS
    // ==========================================

    private void showCustomerDetails() {

        System.out.println(
                "\n========== CUSTOMER DETAILS =========="
        );

        int customerId =
                readInt(
                        "Enter customer ID: "
                );

        Customer customer =
                bankService.getCustomer(
                        customerId
                );

        System.out.println(
                "Customer ID: "
                        + customer.getCustomerId()
        );

        System.out.println(
                "Name: "
                        + customer.getName()
        );

        System.out.println(
                "Phone: "
                        + customer.getPhone()
        );

        System.out.println(
                "Email: "
                        + customer.getEmail()
        );

        System.out.println(
                "Address: "
                        + customer.getAddress()
        );

        System.out.println(
                "\n---------- ACCOUNTS ----------"
        );

        if (customer.getAccounts().isEmpty()) {

            System.out.println(
                    "No account linked."
            );

            return;
        }

        for (Account account :
                customer.getAccounts()) {

            System.out.println(
                    "Account Number: "
                            + account.getAccountNumber()
            );

            System.out.println(
                    "Account Type: "
                            + account.getAccountType()
            );

            System.out.println(
                    "Balance: ₹"
                            + account.getBalance()
            );

            System.out.println(
                    "Status: "
                            + account.getStatus()
            );

            System.out.println(
                    "----------------------------"
            );
        }
    }

    // ==========================================
    // READ INTEGER
    // ==========================================

    private int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! " +
                                "Please enter a number."
                );
            }
        }
    }

    // ==========================================
    // READ LONG
    // ==========================================

    private long readLong(String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine();

            try {

                return Long.parseLong(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! " +
                                "Please enter a valid number."
                );
            }
        }
    }

    // ==========================================
    // READ AMOUNT
    // ==========================================

    private BigDecimal readAmount(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine();

            try {

                BigDecimal amount =
                        new BigDecimal(input);

                if (amount.compareTo(
                        BigDecimal.ZERO) <= 0) {

                    System.out.println(
                            "Amount must be greater than 0."
                    );

                    continue;
                }

                return amount;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid amount! " +
                                "Example: 5000 or 5000.50"
                );
            }
        }
    }
}