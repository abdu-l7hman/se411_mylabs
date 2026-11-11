package edu.psu.se411.lab12_threads;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab12_threads.model.LockingStrategy;
import edu.psu.se411.lab12_threads.simulation.Simulation;
import edu.psu.se411.lab12_threads.simulation.Simulation.Result;

/**
 * Main program 1: FIXED thread pool, locks with resource ordering.
 * Build and run using Maven goal: clean package exec:java
 */
public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
	public static final int NB_TRANSACTIONS = 1_000_000;

	public static void main(String[] args) throws InterruptedException {
		logger.info("Application is starting (fixed thread pool, " + POOL_SIZE + " threads)...");
		System.out.println("FIXED thread pool (" + POOL_SIZE + " threads)");

		for (int nbAccounts : new int[] { 1000, 10 }) {
			Result r = Simulation.run(nbAccounts, NB_TRANSACTIONS, () -> Executors.newFixedThreadPool(POOL_SIZE),
					LockingStrategy.ORDERED, 600);
			System.out.println(r);
			logger.info("Fixed pool: " + r);
		}

		logger.info("Application is closing...");
	}
}
