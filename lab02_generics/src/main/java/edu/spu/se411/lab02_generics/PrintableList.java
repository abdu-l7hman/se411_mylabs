package edu.spu.se411.lab02_generics;

import java.util.Arrays;
import java.util.List;

/**
 * Exercise 1: A generic class that stores items in a List and can print them.
 *
 * @param <T> the type of items stored in this list
 */
public class PrintableList<T> {

    private List<T> items;

    /**
     * Constructs a PrintableList from an array of items.
     *
     * @param itemsArray the array whose elements are stored in the list
     */
    @SafeVarargs
    public PrintableList(T... itemsArray) {
        this.items = Arrays.asList(itemsArray);
    }

    /** Prints all items in the list, one per line. */
    public void printAll() {
        System.out.println("PrintableList contents:");
        for (T item : items) {
            System.out.println("  " + item);
        }
    }

    public List<T> getItems() {
        return items;
    }
}
