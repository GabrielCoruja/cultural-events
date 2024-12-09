package com.ona.culturalevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CulturalEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CulturalEventsApplication.class, args);
	}

}
