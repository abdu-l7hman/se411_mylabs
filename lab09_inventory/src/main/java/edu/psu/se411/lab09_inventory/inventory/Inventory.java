package edu.psu.se411.lab09_inventory.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.psu.se411.lab09_inventory.search.SearchStrategy;

/** A type-safe inventory holding objects of type T. */
public class Inventory<T> {

	private final List<T> items = new ArrayList<>();

	public void addItem(T item) {
		if (item == null) {
			throw new IllegalArgumentException("Cannot add a null item");
		}
		items.add(item);
	}

	/** @return true if the item was in the inventory */
	public boolean removeItem(T item) {
		return items.remove(item);
	}

	/** Read-only view of all the items. */
	public List<T> getAllItems() {
		return Collections.unmodifiableList(items);
	}

	public int size() {
		return items.size();
	}

	/**
	 * Returns the items that match the given strategy.
	 * "? super T" lets a strategy written for a parent type be reused:
	 * a SearchStrategy<Item> also works on an Inventory<Book>.
	 */
	public List<T> findItems(SearchStrategy<? super T> strategy) {
		List<T> result = new ArrayList<>();
		for (T item : items) {
			if (strategy.matches(item)) {
				result.add(item);
			}
		}
		return result;
	}
}
