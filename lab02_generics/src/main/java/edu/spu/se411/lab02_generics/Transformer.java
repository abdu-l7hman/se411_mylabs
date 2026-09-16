package edu.spu.se411.lab02_generics;

/**
 * Exercise 3: Transformer interface
 */
public interface Transformer<T, R> {
    R transform(T input);
}
