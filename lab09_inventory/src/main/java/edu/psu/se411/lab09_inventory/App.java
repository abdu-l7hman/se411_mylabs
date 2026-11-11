package edu.psu.se411.lab09_inventory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.psu.se411.lab09_inventory.inventory.Inventory;
import edu.psu.se411.lab09_inventory.model.Book;
import edu.psu.se411.lab09_inventory.model.ElectronicDevice;
import edu.psu.se411.lab09_inventory.model.Item;
import edu.psu.se411.lab09_inventory.search.SearchByAuthor;
import edu.psu.se411.lab09_inventory.search.SearchByCategory;
import edu.psu.se411.lab09_inventory.search.SearchById;
import edu.psu.se411.lab09_inventory.search.SearchByName;
import edu.psu.se411.lab09_inventory.search.SearchStrategy;

public class App {

	static Logger logger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		logger.info("Application is starting...");

		try {
			// ---------- Part 1: a generic inventory of books and electronic devices ----------
			Inventory<Item> inventory = new Inventory<>();
			inventory.addItem(new Book(1, "Concurrency in Java", "Brian Goetz"));
			inventory.addItem(new Book(2, "Effective Java", "Joshua Bloch"));
			inventory.addItem(new Book(3, "Java Puzzlers", "Joshua Bloch"));
			inventory.addItem(new ElectronicDevice(4, "Galaxy S25", "Phone"));
			inventory.addItem(new ElectronicDevice(5, "iPhone 17", "Phone"));
			inventory.addItem(new ElectronicDevice(6, "ThinkPad X1", "Laptop"));

			ElectronicDevice oldTablet = new ElectronicDevice(7, "Old Tablet", "Tablet");
			inventory.addItem(oldTablet);
			inventory.removeItem(oldTablet); // remove an item

			// ---------- Part 3: display any inventory ----------
			System.out.println("=== Inventory contents ===");
			displayInventory(inventory);

			// ---------- Part 2: searching with strategies ----------
			System.out.println("\n=== Search by name: \"Concurrency in Java\" ===");
			print(inventory.findItems(new SearchByName("Concurrency in Java")));

			System.out.println("\n=== Search by name: \"ThinkPad X1\" ===");
			print(inventory.findItems(new SearchByName("ThinkPad X1")));

			System.out.println("\n=== Search by id: 5 ===");
			print(inventory.findItems(new SearchById(5)));

			System.out.println("\n=== Books by author: Joshua Bloch ===");
			print(inventory.findItems(new SearchByAuthor("Joshua Bloch")));

			System.out.println("\n=== Electronic devices in category: Phone ===");
			print(inventory.findItems(new SearchByCategory("Phone")));

			// a lambda expression as a SearchStrategy (functional interface)
			System.out.println("\n=== Lambda: names containing \"Java\" ===");
			SearchStrategy<Item> nameContainsJava = item -> item.getName().contains("Java");
			print(inventory.findItems(nameContainsJava));

			System.out.println("\n=== Lambda: items with an even id ===");
			print(inventory.findItems(item -> item.getId() % 2 == 0));

			// a strategy for Item reused on an inventory of Book only
			Inventory<Book> books = new Inventory<>();
			books.addItem(new Book(10, "Clean Code", "Robert Martin"));
			books.addItem(new Book(11, "Head First Design Patterns", "Eric Freeman"));
			System.out.println("\n=== Inventory<Book> contents ===");
			displayInventory(books);
			System.out.println("\n=== Inventory<Book>, search by name: \"Clean Code\" ===");
			print(books.findItems(new SearchByName("Clean Code")));

			// error case: adding null is rejected
			inventory.addItem(null);
		} catch (IllegalArgumentException e) {
			System.out.println("\nError: " + e.getMessage());
			logger.error("Exception raised: " + e.getMessage());
		}

		logger.info("Application is closing...");
	}

	/**
	 * Part 3: prints the items of ANY inventory (Inventory<Item>, Inventory<Book>, ...).
	 * The unbounded wildcard means we only read the items, as plain objects.
	 */
	public static void displayInventory(Inventory<?> inventory) {
		if (inventory.size() == 0) {
			System.out.println("(empty inventory)");
			return;
		}
		for (Object item : inventory.getAllItems()) {
			System.out.println(" - " + item);
		}
	}

	private static void print(List<? extends Item> items) {
		if (items.isEmpty()) {
			System.out.println(" (no match)");
		}
		for (Item item : items) {
			System.out.println(" - " + item);
		}
	}
}
