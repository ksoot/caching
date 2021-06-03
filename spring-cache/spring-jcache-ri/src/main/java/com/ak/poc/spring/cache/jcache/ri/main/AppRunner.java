package com.ak.poc.spring.cache.jcache.ri.main;


import javax.cache.Cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ak.poc.spring.cache.jcache.ri.model.Book;
import com.ak.poc.spring.cache.jcache.ri.repository.BookService;

@Component
public class AppRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

	@Autowired
	private BookService<String, Book> bookService;

	@Autowired
	Cache cache;

	

	@Override
	public void run(String... args) throws Exception {
		logger.info(".... Fetching isbn-1234");
		logger.info(".....Fetched isbn-1234 -->" + bookService.getByIsbn("isbn-1234"));
		
		logger.info(".... Fetching isbn-4567");
		logger.info(".....Fetched isbn-4567 -->" + bookService.getByIsbn("isbn-4567"));
		
		logger.info(".... Fetching isbn-1234");
		logger.info(".....Fetched isbn-1234 -->" + bookService.getByIsbn("isbn-1234"));
		
		logger.info(".... Fetching 4567");
		logger.info(".....Fetched isbn-4567 -->" + bookService.getByIsbn("isbn-4567"));
		System.out.println(cache);
		logger.info(".... Remove isbn-1234");
		bookService.removeBook("isbn-1234");
		logger.info(".... Removed isbn-1234");
		System.out.println(cache);
		logger.info(".... Update isbn-4567");
		Book b = bookService.getByTitle("isbn-4567");
		b.setTitle("Physics");
		System.out.println(cache);
		bookService.saveOrupdateBook(b);
		logger.info(".... updated isbn-4567");
		System.out.println(cache);
		logger.info(".... Fetching Physics");
		logger.info(".....Fetched Physics -->" + bookService.getByTitle("isbn-4567"));
		System.out.println(cache);
		logger.info(".... Adding isbn-1234");
		Book b1 = new Book("isbn-1234", "Maths");
		bookService.saveOrupdateBook(b1);
		logger.info(".... Added isbn-1234");
		System.out.println(cache);
		logger.info(".... Fetching isbn-1234");
		logger.info(".....Fetched isbn-1234 -->" + bookService.getByIsbn("isbn-1234"));
		
		logger.info(".... Fetching isbn-1234");
		logger.info(".....Fetched isbn-1234 -->" + bookService.getByIsbn("isbn-1234"));
		
		System.out.println(cache.toString());
		System.out.println("FINISHED");

	}

}
