package edu.spu.se411.lab06_logging.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.spu.se411.lab06_logging.exceptions.InsufficientFundsException;

public class WalletAccount {

    private static final Logger logger = LoggerFactory.getLogger(WalletAccount.class);

    private double balance;

    public WalletAccount(double balance) {
        setBalance(balance);
        // Step 5: wallet account created → debug
        logger.debug("WalletAccount created with initial balance: {}", balance);
    }

    /**
     * Withdraws the given amount from the account.
     *
     * @throws InsufficientFundsException if amount exceeds balance
     * @throws IllegalArgumentException   if amount is negative
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount < 0) {
            // AI recommendation #3: log at the throw site (model has full context)
            logger.error("Invalid withdrawal attempt — negative amount: {}", amount);
            throw new IllegalArgumentException("Cannot withdraw negative number: " + amount);

        } else if (amount > balance) {
            // AI recommendation #1: WARN logged HERE (throw site), not inside the exception constructor
            // This gives us full context: what was attempted vs. what was available
            logger.warn("Insufficient funds — attempted: {}, available balance: {}", amount, balance);
            throw new InsufficientFundsException(
                    "Insufficient funds. Your balance is " + balance);

        } else {
            balance -= amount;
            // Step 5: successful withdraw → debug
            logger.debug("Withdrawal of {} successful. Remaining balance: {}", amount, balance);
        }
    }

    /**
     * Deposits the given amount into the account.
     *
     * @throws IllegalArgumentException if amount is negative
     */
    public void deposit(double amount) {
        if (amount < 0) {
            // AI recommendation #3: log at the throw site
            logger.error("Invalid deposit attempt — negative amount: {}", amount);
            throw new IllegalArgumentException("Cannot deposit negative number: " + amount);
        } else {
            balance += amount;
            // Step 5: successful deposit → debug
            logger.debug("Deposit of {} successful. New balance: {}", amount, balance);
        }
    }

    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative: " + balance);
        }
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
}
