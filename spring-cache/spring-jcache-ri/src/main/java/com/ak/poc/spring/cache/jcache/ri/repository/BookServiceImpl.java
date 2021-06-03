package com.ak.poc.spring.cache.jcache.ri.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.ak.poc.spring.cache.jcache.ri.model.Book;

@Component
public class BookServiceImpl implements BookService<String, Book> {

	@Override
	@Cacheable(cacheNames = "books_cache")
	public Book getByIsbn(String isbn) {
		return null;
	}

	@Override
	@Cacheable(cacheNames = "books_cache")
	public Book getByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@CacheEvict(cacheNames = "books_cache", allEntries = false)
	public void removeBook(String isbn) {

	}

	@Override
	@CachePut(cacheNames = "books_cache", key = "#book.isbn")
	public Book saveOrupdateBook(Book book) {
		return book;
	}

	
}
