package com.ak.poc.spring.cache.hazelcast.model;

import java.io.Serializable;

public class Book implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private int id; 
	private String isbn;
	private String title;

	public Book(String isbn, String title) {
		this.isbn = isbn;
		this.title = title;
	}
	
	public Book(int id, String isbn, String title) {
		this.id = id;
		this.isbn = isbn;
		this.title = title;
	}
	
	public int getId() {
		return id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", isbn=" + isbn + ", title=" + title + "]";
	}

}