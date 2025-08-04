package com.osmantopal.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = {"com.osmantopal"})
@ComponentScan(basePackages = {"com.osmantopal"})
@EnableJpaRepositories(basePackages = "com.osmantopal.repository")
public class MostFrequentEnglishWordsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MostFrequentEnglishWordsApplication.class, args);
	}

}
