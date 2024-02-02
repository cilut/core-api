package com.cilut.coreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CoreAPIApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreAPIApplication.class, args);
    }
}
