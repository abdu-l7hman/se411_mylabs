package edu.psu.se411.lab11_threads.model;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;

/**
 * A crypto account. Its methods are deliberately NOT synchronized:
 * the protection is done once, in Transaction (see Transaction.transfer).
 */
public class CryptoAccount {

	private final String id;
	private Double balance;

	public CryptoAccount(String id, Double balance) {
		if (balance < 0) throw new IllegalArgumentException("Account balance cannot be negative");
		this.id = id;
		this.balance = balance;
	}

	public void addToBalance(Double amount) {
		if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
		Double current = balance;   // read
		Thread.yield();             // a context switch here exposes the race condition
		balance = current + amount; // write
	}

	public void retrieveFromBalance(Double amount) throws InsufficientBalanceException {
		if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
		Double current = balance;   // read
		if (current < amount) {
			throw new InsufficientBalanceException(
					"Account " + id + " has insufficient balance: " + current + " < " + amount);
		}
		Thread.yield();             // a context switch here exposes the race condition
		balance = current - amount; // write
	}

	public String getId() {
		return id;
	}

	public Double getBalance() {
		return balance;
	}
}
