package com.ak.poc.spring.cache.ehCache.repository;

import com.ak.poc.spring.cache.ehCache.model.Book;

public interface BookRepository {

	Book getByIsbn(String isbn);
	Book getByTitle(String title);

}
