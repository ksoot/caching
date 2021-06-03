package com.ak.poc.spring.cache.jcache.ri.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

public interface BookService<K,V> {

	@Cacheable(cacheNames = "books_cache")
	V getByIsbn(K isbn);

	@Cacheable(cacheNames = "books_cache")
	V getByTitle(K title);

	@CacheEvict(cacheNames = "books_cache", allEntries = false)
	void removeBook(K isbn);

	@CachePut(cacheNames = "books_cache", key = "#book.isbn")
	V saveOrupdateBook(V book);

}
