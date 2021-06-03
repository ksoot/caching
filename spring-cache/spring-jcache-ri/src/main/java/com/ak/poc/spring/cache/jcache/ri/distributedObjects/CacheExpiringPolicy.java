package com.ak.poc.spring.cache.jcache.ri.distributedObjects;

import javax.cache.expiry.Duration;
import javax.cache.expiry.ExpiryPolicy;

import org.springframework.stereotype.Component;

@Component
public class CacheExpiringPolicy implements ExpiryPolicy{

	@Override
	public Duration getExpiryForCreation() {
		// TODO Auto-generated method stub
		return Duration.FIVE_MINUTES;
	}

	@Override
	public Duration getExpiryForAccess() {
		// TODO Auto-generated method stub
		return Duration.FIVE_MINUTES;
	}

	@Override
	public Duration getExpiryForUpdate() {
		// TODO Auto-generated method stub
		return Duration.FIVE_MINUTES;
	}

}
