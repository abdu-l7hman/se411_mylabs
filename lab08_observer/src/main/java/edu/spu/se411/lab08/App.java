package edu.spu.se411.lab08;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.spu.se411.lab08.observers.DashboardObserver;
import edu.spu.se411.lab08.observers.LoggerObserver;
import edu.spu.se411.lab08.sensors.HumiditySensor;
import edu.spu.se411.lab08.sensors.TemperatureSensor;

/**
 * Build and run with the Maven goal: clean package exec:java
 */
public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		logger.info("Application is starting...");

		// 1- two sensors (subjects) and two observers
		TemperatureSensor temp = new TemperatureSensor("Temperature");
		HumiditySensor humidity = new HumiditySensor("Humidity");

		DashboardObserver dashboard = new DashboardObserver("Dashboard");
		LoggerObserver fileLogger = new LoggerObserver("Logger");

		// 2- register both observers to both sensors
		temp.register(dashboard);
		temp.register(fileLogger);
		humidity.register(dashboard);
		humidity.register(fileLogger);

		// 3- simulate sensor readings changing
		System.out.println("=== Simulating sensor readings ===");
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.printf("--- reading %d ---%n", i + 1);
			temp.setReading(20 + random.nextDouble() * 15);
			humidity.setReading(40 + random.nextDouble() * 20);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("Simulation interrupted: " + e.getMessage());
				Thread.currentThread().interrupt();
				break;
			}
		}

		// 4- unregister: the dashboard stops receiving humidity updates
		System.out.println("\n=== Dashboard unregistered from humidity ===");
		humidity.unregister(dashboard);
		humidity.setReading(55.0); // only the Logger is notified

		// 5- cloning: the clone has its OWN observers
		System.out.println("\n=== Cloning the temperature sensor ===");
		TemperatureSensor tempClone = temp.clone();
		tempClone.setName("Temperature (clone)");
		System.out.printf("Original observers: %d, clone observers: %d%n",
				temp.getObserverCount(), tempClone.getObserverCount());

		DashboardObserver cloneDashboard = new DashboardObserver("Clone dashboard");
		tempClone.register(cloneDashboard);

		System.out.println("Changing the clone's reading (only the clone dashboard should react):");
		tempClone.setReading(30.0);

		System.out.println("Changing the original's reading (only Dashboard and Logger should react):");
		temp.setReading(25.0);

		logger.info("Application is closing...");
	}
}
