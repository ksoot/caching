package com.ak.poc.spring.cache.jcache.ri.distributedObjects;

import java.io.Serializable;
import java.util.Collection;

import javax.cache.Cache.Entry;
import javax.cache.integration.CacheWriter;
import javax.cache.integration.CacheWriterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ak.poc.spring.cache.jcache.ri.repository.BookRepository;

@Component
public class CacheWriteThrough<K,V extends Serializable> implements CacheWriter<K, V>{

	@Autowired
	private BookRepository<K,V> bookRepository;
	
	@Override
	public void write(Entry<? extends K, ? extends V> entry) throws CacheWriterException {
		System.out.println("add book in DB through mapstore");
		bookRepository.saveOrUpdateBookInDB(entry.getValue());
	}

	@Override
	public void writeAll(Collection<Entry<? extends K, ? extends V>> entries) throws CacheWriterException {
		System.out.println("storing all books into DB through mapstore");
		entries.forEach(entry -> bookRepository.saveOrUpdateBookInDB(entry.getValue()));
	}

	@Override
	public void delete(Object key) throws CacheWriterException {
		System.out.println("delete book from DB through mapstore");
		bookRepository.deleteBookFromDB((K) key);
	}

	@Override
	public void deleteAll(Collection<?> keys) throws CacheWriterException {
		System.out.println("deleting all books from DB through mapstore");
		keys.forEach(key -> bookRepository.deleteBookFromDB((K) key));
		
	}

}
