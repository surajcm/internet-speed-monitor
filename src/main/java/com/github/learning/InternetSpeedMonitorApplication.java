package com.github.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InternetSpeedMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetSpeedMonitorApplication.class, args);
	}

}
