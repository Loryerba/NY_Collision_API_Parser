package org.example;

import org.example.service.CollisionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;

@SpringBootApplication
public class Main {
        public static void main(String[] args) {
            SpringApplication.run(Main.class, args);

        }

        @Bean
        CommandLineRunner run(CollisionService service) {
            return args -> {
                service.importCsv("crashes.csv");
                service.reverseGeocoding();
            };
        }
}
