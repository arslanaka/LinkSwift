package com.example.aka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.aka.repo")
@EntityScan("com.example.aka.entity")
public class AkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkaApplication.class, args);
	}

}
