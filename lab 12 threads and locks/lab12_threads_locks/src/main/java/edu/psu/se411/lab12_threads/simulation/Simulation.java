package edu.psu.se411.lab12_threads.simulation;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import edu.psu.se411.lab12_threads.model.CryptoAccount;
import edu.psu.se411.lab12_threads.model.LockingStrategy;
import edu.psu.se411.lab12_threads.model.Transaction;

/**
 * Shared simulation code used by all main programs (code reuse):
 * creates the accounts, submits random transactions to a thread pool,
 * waits for them and checks the total money in the system.
 */
public class Simulation {

	public static final double INITIAL_BALANCE = 10000.0;

	/** Result of one run. */
	public record Result(int nbAccounts, int nbTransactions, double totalBefore, double totalAfter,
			long elapsedMillis, int peakThreads, boolean finished) {

		public boolean moneyConserved() {
			return Math.abs(totalAfter - totalBefore) < 0.001;
		}

		@Override
		public String toString() {
			if (!finished) {
				return String.format("%5d accounts, %,d transactions: did NOT finish (stuck)", nbAccounts,
						nbTransactions);
			}
			return String.format(
					"%5d accounts, %,d transactions: %,6d ms | peak threads %,6d | total before %.2f, after %.2f %s",
					nbAccounts, nbTransactions, elapsedMillis, peakThreads, totalBefore, totalAfter,
					moneyConserved() ? "(conserved)" : "(INCONSISTENT)");
		}
	}

	public static CryptoAccount[] createAccounts(int nbAccounts) {
		CryptoAccount[] accounts = new CryptoAccount[nbAccounts];
		for (int i = 0; i < nbAccounts; i++) {
			accounts[i] = new CryptoAccount(i, INITIAL_BALANCE);
		}
		return accounts;
	}

	public static double sumTotalMoneyInAccounts(CryptoAccount[] accounts) {
		double total = 0.0;
		for (CryptoAccount c : accounts) {
			total += c.getBalance();
		}
		return total;
	}

	/**
	 * Runs nbTransactions random transfers between nbAccounts accounts on the pool
	 * created by poolFactory. Gives up after timeoutSeconds (used to detect a deadlock).
	 */
	public static Result run(int nbAccounts, int nbTransactions, Supplier<ExecutorService> poolFactory,
			LockingStrategy strategy, long timeoutSeconds) throws InterruptedException {

		CryptoAccount[] accounts = createAccounts(nbAccounts);
		double totalBefore = sumTotalMoneyInAccounts(accounts);

		ThreadMXBean threads = ManagementFactory.getThreadMXBean();
		threads.resetPeakThreadCount();
		ExecutorService executor = poolFactory.get();
		long start = System.currentTimeMillis();

		for (int i = 0; i < nbTransactions; i++) {
			int senderIndex = (int) (Math.random() * nbAccounts);
			int receiverIndex = (int) (Math.random() * nbAccounts);
			if (senderIndex == receiverIndex) {
				receiverIndex = (receiverIndex + 1) % nbAccounts; // sender and receiver must differ
			}
			double amount = Math.random() * 100;
			executor.execute(new Transaction("t_" + i, accounts[senderIndex], accounts[receiverIndex], amount,
					strategy));
		}

		executor.shutdown();
		boolean finished = executor.awaitTermination(timeoutSeconds, TimeUnit.SECONDS);
		long elapsed = System.currentTimeMillis() - start;

		if (!finished) {
			executor.shutdownNow(); // threads blocked in lock() are not interruptible; they stay stuck
		}
		return new Result(nbAccounts, nbTransactions, totalBefore, sumTotalMoneyInAccounts(accounts), elapsed,
				threads.getPeakThreadCount(), finished);
	}
}
