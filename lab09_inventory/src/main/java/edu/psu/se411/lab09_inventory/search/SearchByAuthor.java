package edu.psu.se411.lab09_inventory.search;

import edu.psu.se411.lab09_inventory.model.Book;
import edu.psu.se411.lab09_inventory.model.Item;

/** Matches books written by the given author; other items never match. */
public class SearchByAuthor implements SearchStrategy<Item> {

	private final String author;

	public SearchByAuthor(String author) {
		this.author = author;
	}

	@Override
	public boolean matches(Item item) {
		return item instanceof Book book && book.getAuthor().equalsIgnoreCase(author);
	}
}
