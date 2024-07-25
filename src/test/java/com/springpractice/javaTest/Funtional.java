package com.springpractice.javaTest;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Funtional {

    @Test
    void test() {
        // Supplier<String> supplier = () -> "Hello";
        // supplier.get();

         Supplier<String> instance = new Supplier<>() {
             @Override
             public String get() {
                 return "???";
             }
         };

         System.out.println(instance.get());

         Supplier<String> instance2 = () -> "HEllo!";

        System.out.println(instance2.get());

        Consumer<String> consumer = (s) -> System.out.println(s + "!!!!!!!");

        consumer.accept("Hello");
    }
}
