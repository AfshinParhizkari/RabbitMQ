package com.codeusingjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SpringbootRabbitmq {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringbootRabbitmq.class);
	
	public static void main(String[] args) {
		// The "run()" method returns the "ConfigurableApplicationContext" instance which can be further used by the spring application.
		SpringApplication.run(SpringbootRabbitmq.class, args);
		
	}
}
