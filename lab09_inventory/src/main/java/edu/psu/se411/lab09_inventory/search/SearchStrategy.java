package edu.psu.se411.lab09_inventory.search;

/**
 * Strategy: decides whether an item matches a search criteria.
 * It has a single abstract method, so it is a functional interface
 * and can be implemented with a lambda expression.
 */
@FunctionalInterface
public interface SearchStrategy<T> {

	boolean matches(T item);
}
