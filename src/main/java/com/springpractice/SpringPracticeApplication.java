package com.springpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@ConfigurationPropertiesScan
public class SpringPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPracticeApplication.class, args);
    }

}
