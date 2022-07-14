package io.github.ignamlrz.autotrader.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class AutotraderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutotraderServiceApplication.class, args);
	}

}
