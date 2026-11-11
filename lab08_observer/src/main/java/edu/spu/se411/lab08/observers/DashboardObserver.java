package edu.spu.se411.lab08.observers;

import edu.spu.se411.lab08.sensors.Sensor;

/** Displays the latest value of each sensor it observes. */
public class DashboardObserver extends SensorObserver {

	public DashboardObserver(String name) {
		super(name);
	}

	@Override
	protected void onSensorUpdate(Sensor sensor) {
		System.out.printf("[%s] display: %s%n", getName(), sensor);
	}
}
