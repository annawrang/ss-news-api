package com.sisterside.ssnewsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.sisterside.ssnewsapi")
public class SsNewsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsNewsApiApplication.class, args);
	}
}
