package com.batch.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		log.info("Starting SpringBatch File Processing Application");
		SpringApplication.run(MainApplication.class, args);
		log.info("SpringBatch File Processing Application started successfully");
	}

}
