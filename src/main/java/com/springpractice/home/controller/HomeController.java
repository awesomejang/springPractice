package com.springpractice.home.controller;

import com.springpractice.annotation.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @LogExecutionTime("home")
    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }
}
