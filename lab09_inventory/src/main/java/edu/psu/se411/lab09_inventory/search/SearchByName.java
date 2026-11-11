package edu.psu.se411.lab09_inventory.search;

import edu.psu.se411.lab09_inventory.model.Item;

/** Matches items whose name equals the given name (case-insensitive). */
public class SearchByName implements SearchStrategy<Item> {

	private final String name;

	public SearchByName(String name) {
		this.name = name;
	}

	@Override
	public boolean matches(Item item) {
		return item.getName().equalsIgnoreCase(name);
	}
}
