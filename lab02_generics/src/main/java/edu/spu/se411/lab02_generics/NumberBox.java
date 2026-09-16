package edu.spu.se411.lab02_generics;

import java.util.List;

/**
 * Exercise 2: Bounded Type Parameters (generic wrapper class)
 */
public class NumberBox<T extends Number> {
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    /**
     * Adds the value of this NumberBox to another NumberBox's value.
     */
    public double add(NumberBox<? extends Number> other) {
        if (this.item == null || other.getItem() == null) {
            return 0.0;
        }
        return this.item.doubleValue() + other.getItem().doubleValue();
    }
}
