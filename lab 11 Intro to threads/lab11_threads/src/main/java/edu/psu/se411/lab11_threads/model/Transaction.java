package edu.psu.se411.lab11_threads.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab11_threads.exceptions.InsufficientBalanceException;

/**
 * A payment from a sender account to a receiver account.
 *
 * Usage (as required by the lab):
 *   Transaction t = new Transaction(randomA, randomB, randomAmount);
 *   t.execute();
 *
 * Implements Runnable so each transaction can run in its own thread.
 */
public class Transaction implements Runnable {

	static Logger logger = LoggerFactory.getLogger(Transaction.class);

	private final String id;
	private final CryptoAccount sender;
	private final CryptoAccount receiver;
	private final Double amount;
	private final boolean synchronizedMode;

	/** Transaction protected by synchronization (the fixed version). */
	public Transaction(CryptoAccount sender, CryptoAccount receiver, Double amount) {
		this(sender, receiver, amount, true);
	}

	/**
	 * @param synchronizedMode false = part 1 of the lab (no synchronization, shows the problem),
	 *                         true  = part 2 of the lab (synchronized solution)
	 */
	public Transaction(CryptoAccount sender, CryptoAccount receiver, Double amount, boolean synchronizedMode) {
		if (sender == null || receiver == null || amount == null || amount < 0)
			throw new IllegalArgumentException("Invalid transaction parameters");
		this.id = sender.getId() + "->" + receiver.getId();
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.synchronizedMode = synchronizedMode;
	}

	/** Executes the payment. */
	public void execute() {
		try {
			if (synchronizedMode) {
				transfer(sender, receiver, amount);
			} else {
				transferWithoutSynchronization(sender, receiver, amount);
			}
		} catch (InsufficientBalanceException e) {
			logger.warn("Transaction " + id + " skipped: " + e.getMessage());
		}
	}

	@Override
	public void run() {
		execute();
	}

	/**
	 * PART 1 – no synchronization: two threads can read the same balance at the
	 * same time and one update overwrites the other, so money appears or disappears.
	 */
	private static void transferWithoutSynchronization(CryptoAccount sender, CryptoAccount receiver, Double amount)
			throws InsufficientBalanceException {
		sender.retrieveFromBalance(amount);
		receiver.addToBalance(amount);
	}

	/**
	 * PART 2 – the ONE synchronized method.
	 * It is static, so the lock is the Transaction class itself: only one transfer
	 * runs at a time, which makes debit + credit a single atomic step.
	 * (An instance-level synchronized method would not work, because every
	 * thread has its own Transaction object and therefore its own lock.)
	 */
	private static synchronized void transfer(CryptoAccount sender, CryptoAccount receiver, Double amount)
			throws InsufficientBalanceException {
		sender.retrieveFromBalance(amount);
		receiver.addToBalance(amount);
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
