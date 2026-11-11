package edu.spu.se411.lab08.observers;

import edu.spu.se411.lab08.framework.Observer;
import edu.spu.se411.lab08.framework.Subject;
import edu.spu.se411.lab08.sensors.Sensor;

/**
 * Common code for observers that watch sensors: checks the subject is a
 * sensor and hands it to onSensorUpdate(), which each observer implements.
 */
public abstract class SensorObserver implements Observer {

	private final String name;

	protected SensorObserver(String name) {
		this.name = name;
	}

	@Override
	public void update(Subject subject) {
		if (subject instanceof Sensor sensor) {
			onSensorUpdate(sensor);
		}
	}

	protected abstract void onSensorUpdate(Sensor sensor);

	public String getName() {
		return name;
	}
}
