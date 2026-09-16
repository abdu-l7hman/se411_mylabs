package edu.spu.se411.lab02_generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 3: Reusable pipeline
 */
public class PipeLine<T, R> {

    private final List<Transformer<?, ?>> steps;

    private PipeLine(List<Transformer<?, ?>> steps) {
        this.steps = steps;
    }

    /**
     * Creates an empty pipeline where input and output types are the same initially.
     */
    public static <T> PipeLine<T, T> create() {
        return new PipeLine<>(new ArrayList<>());
    }

    /**
     * Adds a new transformer to the pipeline, returning a new pipeline with the updated output type.
     */
    public <NewR> PipeLine<T, NewR> add(Transformer<R, NewR> transformer) {
        List<Transformer<?, ?>> newSteps = new ArrayList<>(this.steps);
        newSteps.add(transformer);
        return new PipeLine<>(newSteps);
    }

    /**
     * Applies all transformations in order.
     */
    @SuppressWarnings("unchecked")
    public R execute(T input) {
        Object current = input;
        for (Transformer<?, ?> step : steps) {
            current = ((Transformer<Object, Object>) step).transform(current);
        }
        return (R) current;
    }
}
