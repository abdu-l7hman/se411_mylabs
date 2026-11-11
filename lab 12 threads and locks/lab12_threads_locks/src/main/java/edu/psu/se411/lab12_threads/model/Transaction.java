package edu.psu.se411.lab12_threads.model;

import java.util.concurrent.locks.Lock;

import edu.psu.se411.lab12_threads.exceptions.InsufficientBalanceException;

public class Transaction implements Runnable {

	private final String id;
	private final CryptoAccount sender;
	private final CryptoAccount receiver;
	private final Double amount;
	private final LockingStrategy strategy;

	public Transaction(String id, CryptoAccount sender, CryptoAccount receiver, Double amount) {
		this(id, sender, receiver, amount, LockingStrategy.ORDERED);
	}

	public Transaction(String id, CryptoAccount sender, CryptoAccount receiver, Double amount,
			LockingStrategy strategy) {
		if (id == null || sender == null || receiver == null || amount == null || amount < 0 || strategy == null)
			throw new IllegalArgumentException("Invalid transaction parameters");
		if (sender == receiver)
			throw new IllegalArgumentException("Sender and receiver must be different accounts");
		this.id = id;
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.strategy = strategy;
	}

	@Override
	public void run() {
		try {
			execute();
		} catch (InsufficientBalanceException e) {
			// transaction refused: nothing was moved
		}
	}

	public void execute() throws InsufficientBalanceException {
		Lock first;
		Lock second;
		if (strategy == LockingStrategy.ORDERED) {
			// d) Resource ordering: total order on accounts by id, lock in ascending order.
			// Two transfers between the same accounts always lock them in the SAME order,
			// so a circular wait (and therefore a deadlock) is impossible.
			boolean senderFirst = sender.compareTo(receiver) < 0;
			first = senderFirst ? sender.getLock() : receiver.getLock();
			second = senderFirst ? receiver.getLock() : sender.getLock();
		} else {
			// b) Naive locking: sender then receiver.
			// c) If T1 does A->B while T2 does B->A, T1 holds A and waits for B while
			// T2 holds B and waits for A: DEADLOCK, both threads wait forever.
			first = sender.getLock();
			second = receiver.getLock();
		}

		first.lock();
		try {
			second.lock();
			try {
				sender.retrieveFromBalance(amount);
				receiver.addToBalance(amount);
			} finally {
				second.unlock();
			}
		} finally {
			first.unlock(); // always released, even if an exception is thrown
		}
	}

	public String getId() {
		return id;
	}

	public CryptoAccount getSender() {
		return sender;
	}

	public CryptoAccount getReceiver() {
		return receiver;
	}

	public Double getAmount() {
		return amount;
	}
}
