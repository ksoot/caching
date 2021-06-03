package com.ak.poc.spring.cache.hazelcast.configuration;

import javax.sql.DataSource;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import com.ak.poc.spring.cache.hazelcast.distributedObjects.MyMapStore;
import com.ak.poc.spring.cache.hazelcast.model.Book;
import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig.InitialLoadMode;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.UserCodeDeploymentConfig.ClassCacheMode;
import com.hazelcast.config.UserCodeDeploymentConfig.ProviderMode;
import com.hazelcast.spring.context.SpringManagedContext;

@Configuration
public class SpringHazelcastConfiguration {

	/**
	 * @return Cache This is spring Cache for ehCache and holds ehCache manager in
	 *         it
	 * @param EhCacheCacheManager This is spring CacheManager implementation for
	 *                            ehCache and holds ehCache manager in it
	 * @param CacheConfiguration  This is ehCache configuration class.
	 */

	@Bean
	public SpringManagedContext managedContext() {
		return new SpringManagedContext();
	}

	@Bean
	public Cache createCache(CacheManager cm) {
		Cache c = cm.getCache("books_cache");
		return c;
	}

	@Bean
	public Config createHazelcastConfig(SpringManagedContext managedContext) {
		Config config = new Config();

		config.setClusterName("dev");
		config.setInstanceName("ankit machine");

		config.setClassLoader(Thread.currentThread().getContextClassLoader());
		config.getUserCodeDeploymentConfig().setEnabled(true);
		config.getUserCodeDeploymentConfig().setClassCacheMode(ClassCacheMode.ETERNAL);
		config.getUserCodeDeploymentConfig().setProviderMode(ProviderMode.LOCAL_AND_CACHED_CLASSES);

		config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
		config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost");

		config.getNetworkConfig().setPort(5701);
		config.getNetworkConfig().setPortAutoIncrement(false);

		config.addListenerConfig(new ListenerConfig().setImplementation(new MyMemberShipListener()));
		config.addListenerConfig(new ListenerConfig().setImplementation(new MyDistributedObjectListener()));
		config.addListenerConfig(new ListenerConfig().setImplementation(new MyMigrationListener()));
		config.addListenerConfig(new ListenerConfig().setImplementation(new MyLifecycleListener()));
		config.addListenerConfig(new ListenerConfig().setImplementation(new MyClientListener()));
		
		config.setManagedContext(managedContext);
		config.addMapConfig(mapConfig());

		return config;
	}

	private MapConfig mapConfig() {
		MapConfig mapConfig = new MapConfig("books_cache");
		mapConfig.setBackupCount(1);
		mapConfig.setTimeToLiveSeconds(60);
		mapConfig.setEvictionConfig(new EvictionConfig().setEvictionPolicy(EvictionPolicy.LRU)
				.setMaxSizePolicy(MaxSizePolicy.PER_NODE).setSize(1));
		mapConfig.addEntryListenerConfig(new EntryListenerConfig().setImplementation(new MyMapEventListener<String,Book>()));
		mapConfig.getMapStoreConfig().setImplementation(new MyMapStore<String, Book>()).setEnabled(true).setInitialLoadMode(InitialLoadMode.EAGER);

		return mapConfig;
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
