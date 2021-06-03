package com.ak.poc.spring.cache.hazelcast.configuration;

import com.ak.poc.spring.cache.hazelcast.distributedObjects.MyMapStore;
import com.ak.poc.spring.cache.hazelcast.model.Book;
import com.hazelcast.config.Config;
import com.hazelcast.config.EntryListenerConfig;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ListenerConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.MapStoreConfig.InitialLoadMode;
import com.hazelcast.config.UserCodeDeploymentConfig.ClassCacheMode;
import com.hazelcast.config.UserCodeDeploymentConfig.ProviderMode;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.logging.ILogger;
import com.hazelcast.map.IMap;

public class Test {

	 public static void main(String[] args) {
		 
		 Config config = new Config();

			config.setClassLoader(Thread.currentThread().getContextClassLoader());
			config.getUserCodeDeploymentConfig().setEnabled(true);
			config.getUserCodeDeploymentConfig().setClassCacheMode(ClassCacheMode.ETERNAL);
			config.getUserCodeDeploymentConfig().setProviderMode(ProviderMode.LOCAL_AND_CACHED_CLASSES);

			config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
			config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
			config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost");

			config.getNetworkConfig().setPort(5701);
			config.getNetworkConfig().setPortAutoIncrement(false);


			MapConfig mapConfig = new MapConfig("defaultMap");
			mapConfig.setBackupCount(3);
			mapConfig.setTimeToLiveSeconds(60);
			mapConfig.setEvictionConfig(new EvictionConfig().setEvictionPolicy(EvictionPolicy.LRU)
					.setMaxSizePolicy(MaxSizePolicy.PER_NODE).setSize(1));
			config.addMapConfig(mapConfig);
			
			
	        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
	        ILogger logger = instance.getLoggingService().getLogger(Test.class);
	       
	        IMap<String, String> defaultMap = instance.getMap("defaultMap");
	        
	        MapConfig defaultMapConfig = instance.getConfig().getMapConfig("defaultMap");
	        
	        logger.info("Map \"defaultMapConfig\" has backup count " + defaultMapConfig.getBackupCount());
	        logger.info("Map \"defaultMapConfig\" has ttl count " + defaultMapConfig.getTimeToLiveSeconds());
	        logger.info("defaultMap[\"1\"] = " + defaultMap.get("1"));
	        
	        instance.getConfig().getMapConfig("defaultMap").setBackupCount(2);
	        instance.getConfig().getMapConfig("defaultMap").setTimeToLiveSeconds(3000);
	        
	        MapConfig mapWithLoaderConfig = new MapConfig("map-with-loader-*").setBackupCount(0);
	        mapWithLoaderConfig.setTimeToLiveSeconds(5000);

	        instance.getConfig().addMapConfig(mapWithLoaderConfig);

	        IMap<String, String> mapWithLoader1 = instance.getMap("map-with-loader-1");
	        
	        MapConfig mapWithLoader1Config = instance.getConfig().getMapConfig("map-with-loader-1");
	        
	        logger.info("Map \"mapWithLoader1\" has backup count " + mapWithLoader1Config.getBackupCount());
	        logger.info("Map \"mapWithLoader1\" has ttl count " + mapWithLoader1Config.getTimeToLiveSeconds());
	        logger.info("Map \"defaultMapConfig\" has backup count " + defaultMapConfig.getBackupCount());
	        logger.info("Map \"defaultMapConfig\" has ttl count " + defaultMapConfig.getTimeToLiveSeconds());
	        logger.info("mapWithLoader1[\"1\"] = " + mapWithLoader1.get("1") + " (loaded from configured map loader)");

	        instance.shutdown();
	    }
}
