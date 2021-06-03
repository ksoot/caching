package com.ak.poc.spring.cache.hazelcast.distributedObjects;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.ak.poc.spring.cache.hazelcast.model.Book;
import com.ak.poc.spring.cache.hazelcast.repository.BookRepository;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.MapLoaderLifecycleSupport;
import com.hazelcast.map.MapStore;
import com.hazelcast.spring.context.SpringAware;

@SpringAware
public class MyMapStore<K, V extends Serializable> implements MapStore<K, V>, MapLoaderLifecycleSupport{

	@Autowired
	private BookRepository<K,V> bookRepository;
	
	@Override
	public void init(HazelcastInstance hazelcastInstance, Properties properties, String mapName) {
	    hazelcastInstance.getConfig().getManagedContext().initialize(this);
	  //  loadAll((Collection<K>) loadAllKeys());
	}
	
	@Override
	public V load(K key) {
		System.out.println("getting book from DB through mapstore");
		return bookRepository.findOneByIsbnFromDB(key);
	}

	@Override
	public Map<K, V> loadAll(Collection<K> keys) {
		System.out.println("getting all books from DB through mapstore");
		Map<K,V> map = new HashMap<K, V>();
		for (K key : keys) {
			map.put(key, bookRepository.findOneByIsbnFromDB(key));
		}
		return map;
	}

	@Override
	public Iterable<K> loadAllKeys() {
		System.out.println("loading all isbn from DB through mapstore");
		return (Iterable<K>) bookRepository.findAllBooksFromDB().stream().map(book -> ((Book) book).getIsbn()).collect(Collectors.toList());
	}

	@Override
	public void store(K key, V book) {
		System.out.println("add book in DB through mapstore");
		bookRepository.saveOrUpdateBookInDB(book);
	}

	@Override
	public void storeAll(Map<K, V> map) {
		System.out.println("storing all books into DB through mapstore");
		 map.forEach((k, v) -> bookRepository.saveOrUpdateBookInDB(v));
	}

	@Override
	public void delete(K key) {
		System.out.println("delete book from DB through mapstore");
		bookRepository.deleteBookFromDB(key);
	}

	@Override
	public void deleteAll(Collection<K> keys) {
		System.out.println("deleting all books from DB through mapstore");
		for (K key : keys) {
			bookRepository.deleteBookFromDB(key);
		}
	}

	@Override
	public void destroy() {
	}

	
}
