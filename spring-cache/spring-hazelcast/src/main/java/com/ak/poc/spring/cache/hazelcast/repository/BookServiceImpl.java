package com.ak.poc.spring.cache.hazelcast.repository;

import org.springframework.stereotype.Component;

import com.ak.poc.spring.cache.hazelcast.model.Book;

@Component
public class BookServiceImpl implements BookService<String, Book> {

	@Override
	public Book getByIsbn(String isbn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Book getByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeBook(String isbn) {
		// TODO Auto-generated method stub

	}

	@Override
	public Book saveOrupdateBook(Book book) {
		return book;
	}

	
}
