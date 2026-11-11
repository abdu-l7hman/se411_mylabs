package edu.psu.se411.lab12_threads.model;

public enum LockingStrategy {
	/** b) lock the sender, then the receiver: can DEADLOCK (question c). */
	NAIVE,
	/** d) resource ordering: always lock the account with the smaller id first. */
	ORDERED
}
