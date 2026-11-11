package edu.psu.se411.lab12_threads;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab12_threads.model.LockingStrategy;
import edu.psu.se411.lab12_threads.simulation.Simulation;
import edu.psu.se411.lab12_threads.simulation.Simulation.Result;

/**
 * Main program 2 (question a): same simulation with a CACHED thread pool.
 * Run with: clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppCachedThreadPool
 */
public class AppCachedThreadPool {

	static Logger logger = LoggerFactory.getLogger(AppCachedThreadPool.class);

	public static void main(String[] args) throws InterruptedException {
		logger.info("Application is starting (cached thread pool)...");
		System.out.println("CACHED thread pool");

		for (int nbAccounts : new int[] { 1000, 10 }) {
			Result r = Simulation.run(nbAccounts, App.NB_TRANSACTIONS, Executors::newCachedThreadPool,
					LockingStrategy.ORDERED, 600);
			System.out.println(r);
			logger.info("Cached pool: " + r);
		}

		logger.info("Application is closing...");
	}
}
