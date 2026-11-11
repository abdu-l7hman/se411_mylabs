package edu.psu.se411.lab11_threads;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab11_threads.model.CryptoAccount;
import edu.psu.se411.lab11_threads.model.Transaction;

/**
 * Lab 11 – The double spending problem.
 * Build and run using Maven goal: clean package exec:java
 *
 * Part 1: 10 000 threads make random transactions WITHOUT synchronization
 *         -> the total money in the system is usually no longer 10 000 riyals.
 * Part 2: same simulation WITH the synchronized method
 *         -> the total always stays 10 000 riyals.
 */
public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static final int NB_ACCOUNTS = 10;
	public static final double INITIAL_BALANCE = 1000.0; // 10 x 1000 = 10 000 riyals
	public static final int NB_TRANSACTIONS = 10000;

	public static void main(String[] args) throws InterruptedException {
		logger.info("Application is starting...");

		System.out.println("===== Part 1: WITHOUT synchronization =====");
		runSimulation(false);

		System.out.println();
		System.out.println("===== Part 2: WITH synchronized method =====");
		runSimulation(true);

		logger.info("Application is closing...");
	}

	/** Creates 10 accounts and 10 000 threads, each executing one random transaction. */
	static void runSimulation(boolean useSynchronization) throws InterruptedException {
		List<CryptoAccount> accounts = new ArrayList<>();
		for (int i = 0; i < NB_ACCOUNTS; i++) {
			accounts.add(new CryptoAccount("acc_" + i, INITIAL_BALANCE));
		}

		double totalBefore = calculateTotal(accounts);
		System.out.printf("Total before: %.2f riyals%n", totalBefore);

		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < NB_TRANSACTIONS; i++) {
			CryptoAccount randomA = accounts.get((int) (Math.random() * 10));
			CryptoAccount randomB = accounts.get((int) (Math.random() * 10));
			Double randomAmount = (double) (Math.random() * 10 + 1);

			Transaction t = new Transaction(randomA, randomB, randomAmount, useSynchronization);
			Thread thread = new Thread(t); // t.execute() is called from run()
			threads.add(thread);
			thread.start();
		}

		// wait for all 10 000 transactions to finish before counting the money
		for (Thread thread : threads) {
			thread.join();
		}

		double totalAfter = calculateTotal(accounts);
		System.out.printf("Total after : %.2f riyals%n", totalAfter);

		if (Math.abs(totalAfter - totalBefore) < 0.001) {
			System.out.println("Money is conserved: balances are consistent.");
			logger.info("Simulation (synchronized=" + useSynchronization + "): money conserved, total = " + totalAfter);
		} else {
			System.out.printf("INCONSISTENT balances: %.2f riyals were created or lost!%n", totalAfter - totalBefore);
			logger.warn("Simulation (synchronized=" + useSynchronization + "): inconsistent total = " + totalAfter);
		}
	}

	/** Total amount of money in the crypto currency system. */
	public static double calculateTotal(List<CryptoAccount> accounts) {
		double total = 0.0;
		for (CryptoAccount acc : accounts) {
			total += acc.getBalance();
		}
		return total;
	}
}
