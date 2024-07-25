package com.springpractice.home;

import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

@RestController
public class HomeController {

    void test() {
        Supplier<String> supplier = () -> "Hello";
        supplier.get();

        Supplier<String> instance = new Supplier<>() {
            @Override
            public String get() {
                return "???";
            }
        };

        System.out.println(instance.get());
    }
}
