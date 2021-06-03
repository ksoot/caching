package com.ak.poc.spring.cache.jcache.ri.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication(scanBasePackages = "com.ak.poc.spring.cache.jcache")
@EnableCaching
public class JCacheRIStarter {

		
	public static void main(String[] args) {
		SpringApplication.run(JCacheRIStarter.class, args);
		System.out.println("");
	}
}
