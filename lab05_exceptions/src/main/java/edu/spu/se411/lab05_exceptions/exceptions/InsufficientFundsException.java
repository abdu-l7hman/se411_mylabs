package edu.spu.se411.lab05_exceptions.exceptions;

/**
 * Custom exception thrown when a withdrawal amount exceeds the wallet balance.
 */
public class InsufficientFundsException extends Exception {

    private final double balance;
    private final double withdrawalAmount;

    public InsufficientFundsException(String message, double balance, double withdrawalAmount) {
        super(message);
        this.balance = balance;
        this.withdrawalAmount = withdrawalAmount;
    }

    public double getBalance() {
        return balance;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }
}
