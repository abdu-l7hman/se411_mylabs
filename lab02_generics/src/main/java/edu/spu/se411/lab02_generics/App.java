package edu.spu.se411.lab02_generics;

import java.util.Arrays;
import java.util.List;

public class App {

    public static void main(String[] args) {
        System.out.println("=== Exercise 1: PrintableList ===");
        PrintableList<String> stringList = new PrintableList<>("Hello", "Java", "Generics");
        stringList.printAll();

        System.out.println("\n=== Exercise 2: NumberBox ===");
        NumberBox<Integer> intBox = new NumberBox<>();
        intBox.setItem(42);
        System.out.println("Integer Box contains: " + intBox.getItem());

        NumberBox<Double> doubleBox = new NumberBox<>();
        doubleBox.setItem(3.14);
        System.out.println("Double Box contains: " + doubleBox.getItem());
        System.out.println("Sum of intBox and doubleBox: " + intBox.add(doubleBox));

        System.out.println("\n=== Exercise 3: PipeLine ===");
        PipeLine<String, Integer> pipeline = PipeLine.<String>create()
                .add(new Transformer<String, String>() {
                    @Override
                    public String transform(String input) {
                        return input.trim();
                    }
                })
                .add(new Transformer<String, Integer>() {
                    @Override
                    public Integer transform(String input) {
                        return input.length();
                    }
                });

        String inputString = "   Generics Pipeline   ";
        Integer result = pipeline.execute(inputString);
        System.out.println("Input: '" + inputString + "'");
        System.out.println("Pipeline Result (length after trim): " + result);

        System.out.println("\n=== Exercise 4: Wildcards ===");
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        System.out.println("Printing list with wildcards:");
        printList(names);

        List<Integer> integers = Arrays.asList(10, 20, 30);
        List<Double> doubles = Arrays.asList(1.5, 2.5, 3.5);

        System.out.println("Sum of integers: " + sumNumbers(integers));
        System.out.println("Sum of doubles: " + sumNumbers(doubles));
    }

    /**
     * Exercise 4: Method to print a list of any type.
     */
    public static void printList(List<?> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }

    /**
     * Exercise 4: Method to calculate sum of a list of Numbers.
     */
    public static double sumNumbers(List<? extends Number> list) {
        double sum = 0.0;
        for (Number num : list) {
            sum += num.doubleValue();
        }
        return sum;
    }
}
