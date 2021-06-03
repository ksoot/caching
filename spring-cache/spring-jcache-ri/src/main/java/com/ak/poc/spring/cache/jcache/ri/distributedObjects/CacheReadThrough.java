package com.ak.poc.spring.cache.jcache.ri.distributedObjects;

import java.util.HashMap;
import java.util.Map;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ak.poc.spring.cache.jcache.ri.repository.BookRepository;

@Component
public class CacheReadThrough<K,V> implements CacheLoader<K, V>{

	@Autowired
	private BookRepository<K,V> bookRepository;
	
	@Override
	public V load(K key) throws CacheLoaderException {
		System.out.println("getting book from DB through mapstore");
		return bookRepository.findOneByIsbnFromDB(key);
	}

	@Override
	public Map<K, V> loadAll(Iterable<? extends K> keys) throws CacheLoaderException {
		System.out.println("getting all books from DB through mapstore");
		Map<K,V> map = new HashMap<K, V>();
		for (K key : keys) {
			map.put(key, bookRepository.findOneByIsbnFromDB(key));
		}
		return map;
	}

}
