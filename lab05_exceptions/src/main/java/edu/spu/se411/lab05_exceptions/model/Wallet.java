package edu.spu.se411.lab05_exceptions.model;

import edu.spu.se411.lab05_exceptions.exceptions.InsufficientFundsException;

/**
 * Represents an online wallet that holds a balance.
 * Supports depositing funds and withdrawing money to a bank account.
 */
public class Wallet {

    private double balance;
    private final String ownerName;

    public Wallet(String ownerName, double initialBalance) {
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative: " + initialBalance);
        }
        this.ownerName = ownerName;
        this.balance = initialBalance;
    }

    /**
     * Deposits the given amount into the wallet.
     *
     * @param amount the positive amount to deposit
     * @throws IllegalArgumentException if amount is zero or negative
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive. Got: " + amount);
        }
        balance += amount;
        System.out.printf("[%s] Deposit of $%.2f successful. New balance: $%.2f%n", ownerName, amount, balance);
    }

    /**
     * Withdraws the given amount from the wallet and transfers it to the bank account.
     *
     * @param amount the positive amount to withdraw
     * @throws InsufficientFundsException if the wallet does not have enough funds
     * @throws IllegalArgumentException   if amount is zero or negative
     */
    public void withdrawToBankAccount(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive. Got: " + amount);
        }
        if (amount > balance) {
            throw new InsufficientFundsException(
                String.format("Insufficient funds. Tried to withdraw $%.2f but balance is $%.2f.", amount, balance),
                balance,
                amount
            );
        }
        balance -= amount;
        System.out.printf("[%s] Withdrawal of $%.2f to bank account successful. Remaining balance: $%.2f%n",
                ownerName, amount, balance);
    }

    public double getBalance() {
        return balance;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
