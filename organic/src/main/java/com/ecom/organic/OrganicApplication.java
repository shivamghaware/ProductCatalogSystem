package com.ecom.organic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrganicApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(OrganicApplication.class);
		app.addInitializers(new com.ecom.organic.config.DotenvConfig());
		app.run(args);
	}

}
