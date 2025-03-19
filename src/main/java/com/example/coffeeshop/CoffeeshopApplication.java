package com.example.coffeeshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.coffeeshop")
public class CoffeeshopApplication {

	public static void main(String[] args) {


		SpringApplication.run(CoffeeshopApplication.class, args);
	}
//	controller (<-service, private repository) -> service (<- serviceImpl) -> respository (<- jpa) -> db
}
