package edu.psu.se411.lab12_threads;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab12_threads.model.LockingStrategy;
import edu.psu.se411.lab12_threads.simulation.Simulation;
import edu.psu.se411.lab12_threads.simulation.Simulation.Result;

/**
 * Question c: "What could go wrong?" when locks replace synchronized.
 * Locking sender-then-receiver without an order leads to a DEADLOCK.
 * Run with: clean package exec:java -Dexec.mainClass=edu.psu.se411.lab12_threads.AppDeadlockDemo
 */
public class AppDeadlockDemo {

	static Logger logger = LoggerFactory.getLogger(AppDeadlockDemo.class);

	public static void main(String[] args) throws InterruptedException {
		logger.info("Application is starting (deadlock demo)...");

		System.out.println("1) NAIVE locking (sender then receiver), 10 accounts:");
		Result naive = Simulation.run(10, App.NB_TRANSACTIONS, () -> Executors.newFixedThreadPool(App.POOL_SIZE),
				LockingStrategy.NAIVE, 10);
		System.out.println("   " + naive);

		ThreadMXBean mx = ManagementFactory.getThreadMXBean();
		long[] deadlocked = mx.findDeadlockedThreads();
		if (deadlocked != null) {
			System.out.println("   DEADLOCK detected: " + deadlocked.length
					+ " threads are each waiting for a lock held by another one.");
			logger.error("Deadlock detected with naive locking: " + deadlocked.length + " threads blocked");
		}

		System.out.println("2) ORDERED locking (resource ordering by account id), 10 accounts:");
		Result ordered = Simulation.run(10, App.NB_TRANSACTIONS, () -> Executors.newFixedThreadPool(App.POOL_SIZE),
				LockingStrategy.ORDERED, 600);
		System.out.println("   " + ordered);

		logger.info("Application is closing...");
		// the deadlocked threads from part 1 can never finish, so we must exit explicitly
		System.exit(0);
	}
}
