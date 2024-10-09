package com.Backend_Traini8.Rahaman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class BackendTraini8RahamanApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendTraini8RahamanApplication.class, args);
	}

}
