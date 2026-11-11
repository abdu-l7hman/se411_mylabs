package edu.spu.se411.lab08.sensors;

import java.util.ArrayList;
import java.util.List;

import edu.spu.se411.lab08.framework.Observer;
import edu.spu.se411.lab08.framework.Subject;

/**
 * A sensor is a Subject: every time its reading changes, all registered
 * observers are notified. All the observer management lives here so the
 * concrete sensors only differ by their name and unit (code reuse).
 *
 * The sensor is Cloneable: a clone copies the name, unit and reading but
 * starts with its OWN empty list of observers.
 */
public abstract class Sensor implements Subject, Cloneable {

	private String name;
	private final String unit;
	private double reading;
	private List<Observer> observers = new ArrayList<>();

	protected Sensor(String name, String unit) {
		this.name = name;
		this.unit = unit;
	}

	@Override
	public void register(Observer o) {
		if (o != null && !observers.contains(o)) {
			observers.add(o);
		}
	}

	@Override
	public void unregister(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		// iterate over a copy so an observer can unregister itself during update()
		for (Observer o : new ArrayList<>(observers)) {
			o.update(this);
		}
	}

	/** Changing the reading notifies every registered observer. */
	public void setReading(double reading) {
		this.reading = reading;
		notifyObservers();
	}

	public double getReading() {
		return reading;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public int getObserverCount() {
		return observers.size();
	}

	@Override
	public Sensor clone() {
		try {
			Sensor copy = (Sensor) super.clone();
			// the clone must NOT share the original's observers
			copy.observers = new ArrayList<>();
			return copy;
		} catch (CloneNotSupportedException e) {
			// cannot happen: Sensor implements Cloneable
			throw new AssertionError(e);
		}
	}

	@Override
	public String toString() {
		return String.format("%s = %.2f %s", name, reading, unit);
	}
}
