package com.pc1.backendrupay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class BackendRupayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendRupayApplication.class, args);
	}

}
