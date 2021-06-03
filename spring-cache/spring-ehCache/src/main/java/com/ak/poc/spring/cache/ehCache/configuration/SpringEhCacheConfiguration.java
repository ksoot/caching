package com.ak.poc.spring.cache.ehCache.configuration;

import javax.sql.DataSource;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.cache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.event.NotificationScope;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;



/**
 * @author ankkumar7
 * This is not an ideal way to implement caching, because here we have hard coded the ehCache class and interfaces 
 * which makes it tightly coupled with ehCache.
 * Solution would be either use of ehcache.xml and provide cache configuration OR
 * Write a wrapper which holds ehCache class & interfaces and we use that wrapper here.
 * CacheManager & Cache are from ehCache. We don't have a way to get empty ehCache cache from spring so we have instantiate it directly here
 *
 */
@Configuration
public class SpringEhCacheConfiguration {

	/**
	 * @return EhCacheCacheManager This is spring CacheManager implementation for ehCache and holds ehCache manager in it
	 * @param CacheManager This is ehCache manager class which is an implementation
	*/
	@Bean
	public EhCacheCacheManager cacheManager(CacheManager cm) {
		EhCacheCacheManager c = new EhCacheCacheManager(cm);
		return c;
	}
	
	
	/**
	 * @return EhCacheManagerFactoryBean This is spring CacheManager factory implementation for ehCache
	*/
	@Bean
	public EhCacheManagerFactoryBean cacheManagerFactory() {
		EhCacheManagerFactoryBean ehCacheFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheFactoryBean.setConfigLocation(new ClassPathResource("/ehcache.xml"));
		return ehCacheFactoryBean;
	}
	
	/**
	 * @return Cache This is spring Cache for ehCache and holds ehCache manager in it
	 * @param EhCacheCacheManager This is spring CacheManager implementation for ehCache and holds ehCache manager in it
	 * @param CacheConfiguration This is ehCache configuration class.
	*/

	@Bean
	public Cache createCache(EhCacheCacheManager cm, CacheConfiguration cacheConfig) {
		CacheManager v = cm.getCacheManager();
		net.sf.ehcache.Cache memoryOnlyCache = new net.sf.ehcache.Cache(cacheConfig); 
		v.addCache(memoryOnlyCache);
		cm.getCacheManager().getEhcache("books_cache").getCacheEventNotificationService().registerListener(new CacheEventLogger(), NotificationScope.ALL);
		System.out.println("----------------------------------"+v.getCache("books_cache"));
		return cm.getCache("books_cache");
	}
	
	/**
	 * @return CacheConfiguration This is ehCache configuration class.
	*/
	@Bean
	public CacheConfiguration createCacheConfig() {
		CacheConfiguration config = new CacheConfiguration(); 
		
		config.name("books_cache").
		maxEntriesLocalHeap(Integer.MAX_VALUE).		
		memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU).
	    eternal(false). 
	    timeToLiveSeconds(60). 
	    timeToIdleSeconds(30). 
	    diskExpiryThreadIntervalSeconds(0); 
	    
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
