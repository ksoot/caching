package com.ak.poc.spring.cache.jcache.ri.configuration;

import javax.cache.Cache;
import javax.cache.configuration.Factory;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import com.ak.poc.spring.cache.jcache.ri.distributedObjects.CacheExpiringPolicy;
import com.ak.poc.spring.cache.jcache.ri.model.Book;

@Configuration
public class SpringJcacheRIConfiguration {

	@Autowired
	CacheLoader<String, Book> loader;
	
	@Autowired
	CacheWriter<String, Book> writer;
	
	@Autowired
	CacheExpiringPolicy policy;
	
	@Bean
	public  Cache<String, Book> createCache(JCacheCacheManager cm, javax.cache.configuration.Configuration<String, Book> config) {
		cm.getCacheManager().createCache("books_cache", config);
		return cm.getCacheManager().getCache("books_cache");
	}
	
	@Bean
	public javax.cache.configuration.Configuration<String, Book> createCacheConfig(){
		javax.cache.configuration.MutableConfiguration<String, Book> config = new MutableConfiguration<String, Book>();
		config.setReadThrough(true);
		config.setStoreByValue(true);
		config.setCacheLoaderFactory(new Factory<CacheLoader<String, Book>>() {

			@Override
			public  CacheLoader<String, Book> create() {
				return  loader;
			}
		});
		config.setCacheWriterFactory(new Factory<CacheWriter<String, Book>>() {

			@Override
			public CacheWriter<String, Book> create() {
				return writer;
			}
		});
		config.setStatisticsEnabled(true);
		config.setExpiryPolicyFactory(new Factory<ExpiryPolicy>() {

			@Override
			public ExpiryPolicy create() {
				return policy;
			}
		});
		
		return config;
	}
	
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType(EmbeddedDatabaseType.H2);
		builder.addScript("classpath:book_schema.sql");
		builder.addScript("classpath:test-data.sql");
		return builder.build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

}
