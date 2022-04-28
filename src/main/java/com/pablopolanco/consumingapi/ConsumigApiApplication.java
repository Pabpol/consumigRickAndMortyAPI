package com.pablopolanco.consumingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConsumigApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumigApiApplication.class, args);
	}

}
