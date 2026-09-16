package edu.spu.se411.lab05_exceptions;

import edu.spu.se411.lab05_exceptions.exceptions.InsufficientFundsException;
import edu.spu.se411.lab05_exceptions.exceptions.InvalidAgeException;
import edu.spu.se411.lab05_exceptions.model.Wallet;

public class App {

    // ─── Exercise 1 ──────────────────────────────────────────────────────────────

    /**
     * Validates that the given age is 18 or older.
     *
     * @param age the age to validate
     * @throws InvalidAgeException if age is less than 18
     */
    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Invalid age: " + age + ". Must be 18 or older.");
        }
        System.out.println("Age valid: " + age + " is 18 or older.");
    }

    // ─── Exercise 2 ──────────────────────────────────────────────────────────────

    /**
     * Demonstrates the online Wallet with withdrawal and exception handling.
     */
    public static void walletDemo() {
        Wallet wallet = new Wallet("Alice", 500.00);

        System.out.println("\n--- Online Wallet Demo ---");
        System.out.printf("Initial balance: $%.2f%n%n", wallet.getBalance());

        // Successful withdrawal
        try {
            wallet.withdrawToBankAccount(200.00);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Deposit
        wallet.deposit(50.00);

        // Withdrawal that exceeds the balance
        try {
            wallet.withdrawToBankAccount(1000.00);
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.printf("  -> Available: $%.2f | Requested: $%.2f%n",
                    e.getBalance(), e.getWithdrawalAmount());
        }

        // Invalid withdrawal (negative) – throws IllegalArgumentException before balance check
        try {
            wallet.withdrawToBankAccount(-50.00);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ─── Main ────────────────────────────────────────────────────────────────────

    public static void main(String[] args) {

        System.out.println("=== Exercise 1: Age Validation ===");

        // Valid age
        try {
            validateAge(25);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Invalid age – too young
        try {
            validateAge(15);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Edge case – exactly 18
        try {
            validateAge(18);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("\n=== Exercise 2: Online Wallet ===");
        walletDemo();
    }
}
