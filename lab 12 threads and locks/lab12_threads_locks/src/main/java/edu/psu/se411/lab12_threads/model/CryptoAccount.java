package edu.psu.se411.lab12_threads.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;

/**
 * Each account owns a Lock (replaces the "synchronized" keyword).
 * The balance methods do not lock by themselves: the Transaction locks
 * BOTH accounts first, so debit + credit happen as one atomic step.
 *
 * Accounts are Comparable by their numeric id: this gives the total order
 * used for resource ordering (always lock the smaller id first).
 */
public class CryptoAccount implements Comparable<CryptoAccount> {

	private final int id;
	private Double balance;
	private final Lock lock = new ReentrantLock();

	public CryptoAccount(int id, Double balance) {
		if (balance < 0) throw new IllegalArgumentException("Account balance cannot be negative");
		this.id = id;
		this.balance = balance;
	}

	public void addToBalance(Double amount) {
		if (amount < 0) throw new IllegalArgumentException("Amount added to balance cannot be negative");
		balance += amount;
	}

	public void retrieveFromBalance(Double amount) throws InsufficientBalanceException {
		if (amount > balance) throw new InsufficientBalanceException(getName());
		balance -= amount;
	}

	public Lock getLock() {
		return lock;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return "acc_" + id;
	}

	public Double getBalance() {
		return balance;
	}

	@Override
	public int compareTo(CryptoAccount other) {
		return Integer.compare(this.id, other.id);
	}
}
