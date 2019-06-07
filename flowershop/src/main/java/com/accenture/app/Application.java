package com.accenture.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Locale;

@SpringBootApplication(scanBasePackages = "java")
public class Application {
    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(Application.class, args);
    }
}