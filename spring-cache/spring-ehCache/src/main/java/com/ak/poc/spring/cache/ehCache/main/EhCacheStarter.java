package com.ak.poc.spring.cache.ehCache.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.ak.poc.spring.cache.ehCache")
@EnableCaching
public class EhCacheStarter {

	public static void main(String[] args) {
		SpringApplication.run(EhCacheStarter.class, args);
	}
}
