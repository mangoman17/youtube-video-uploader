package com.manish.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {

	@Override
	public void run(String... args) {
		System.out.println("Inside run()");
	}

	public static void main(String[] args) {
        System.out.println("Main method()");
		SpringApplication.run(App.class, args);
    }
}