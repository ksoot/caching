package com.ak.poc.spring.cache.hazelcast.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.ak.poc.spring.cache.hazelcast")
@EnableCaching
public class HazelcastStarter {

		
	public static void main(String[] args) {
		SpringApplication.run(HazelcastStarter.class, args);
		System.out.println("");
	}
}
