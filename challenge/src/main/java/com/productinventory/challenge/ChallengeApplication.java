package com.productinventory.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@OpenAPIDefinition
@EntityScan("com.productinventory.challenge.model")
public class ChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

}
