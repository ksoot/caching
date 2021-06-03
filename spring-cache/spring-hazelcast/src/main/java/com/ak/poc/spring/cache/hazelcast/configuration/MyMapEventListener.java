package com.ak.poc.spring.cache.hazelcast.configuration;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.MapEvent;
import com.hazelcast.map.MapPartitionLostEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryEvictedListener;
import com.hazelcast.map.listener.EntryLoadedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.hazelcast.map.listener.MapClearedListener;
import com.hazelcast.map.listener.MapEvictedListener;
import com.hazelcast.map.listener.MapPartitionLostListener;

public class MyMapEventListener<K, V>  implements EntryAddedListener<K, V>,
EntryRemovedListener<K, V>,
EntryUpdatedListener<K, V>,
EntryEvictedListener<K, V>,
EntryLoadedListener<K,V>,
MapEvictedListener,
MapClearedListener,
MapPartitionLostListener{

	@Override
	public void entryAdded(EntryEvent<K, V> event) {
		System.out.println("^^^^^^^Entry added -> "+event);
	}

	@Override
	public void entryUpdated(EntryEvent<K, V> event) {
		System.out.println("^^^^^^^Entry updated -> "+event);
	}

	@Override
	public void entryRemoved(EntryEvent<K, V> event) {
		System.out.println("^^^^^^^Entry removed -> "+event);		
	}

	@Override
	public void entryEvicted(EntryEvent<K, V> event) {
		System.out.println("^^^^^^^Entry evicted -> "+event);		
	}


	@Override
	public void mapCleared(MapEvent event) {
		System.out.println("^^^^^^^map cleared -> "+event);
	}

	@Override
	public void mapEvicted(MapEvent event) {
		System.out.println("^^^^^^^map evicted -> "+event);
		
	}

	@Override
	public void entryLoaded(EntryEvent<K, V> event) {
		System.out.println("^^^^^^^map evicted -> "+event);
		
		}

	@Override
	public void partitionLost(MapPartitionLostEvent event) {
		System.out.println("^^^^^^^partition lost -> "+event);
		
		}

}
