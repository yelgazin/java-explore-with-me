package ru.practicum.ewm.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "ru.practicum.ewm.*")
public class EventServerApp {
    public static void main(String[] args) {
        SpringApplication.run(EventServerApp.class, args);
    }
}
