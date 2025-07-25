package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example"})
public class BishopPrototypeApplication {
    public static void main(String[] args) {
        SpringApplication.run(BishopPrototypeApplication.class, args);
    }
}