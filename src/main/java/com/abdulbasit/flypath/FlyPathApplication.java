package com.abdulbasit.flypath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication

public class FlyPathApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyPathApplication.class, args);
	}

}
