package edu.spu.se411.lab08.observers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.spu.se411.lab08.sensors.Sensor;

/** Writes every sensor change to the log file. */
public class LoggerObserver extends SensorObserver {

	private static final Logger logger = LoggerFactory.getLogger(LoggerObserver.class);

	public LoggerObserver(String name) {
		super(name);
	}

	@Override
	protected void onSensorUpdate(Sensor sensor) {
		System.out.printf("[%s] logged: %s%n", getName(), sensor);
		logger.info("Sensor change: " + sensor);
	}
}
