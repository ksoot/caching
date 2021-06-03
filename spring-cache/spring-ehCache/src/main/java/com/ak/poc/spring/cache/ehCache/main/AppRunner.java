package com.ak.poc.spring.cache.ehCache.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Component;

import com.ak.poc.spring.cache.ehCache.model.Book;
import com.ak.poc.spring.cache.ehCache.repository.BookRepository;

@Component
public class AppRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

	private final BookRepository bookRepository;
	
	@Autowired
	private Cache cache;

	  public AppRunner(BookRepository bookRepository) {
	    this.bookRepository = bookRepository;
	  }

	  @Override
	  public void run(String... args) throws Exception {
	    logger.info(".... Fetching books isbn-1234");
	    logger.info(".....Fetched books isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
	    logger.info(".... Fetching books 4567");
	    logger.info(".....Fetched books isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
	    logger.info(".... Fetching books isbn-1234");
	    logger.info(".....Fetched books isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
	    logger.info(".... Fetching books 4567");
	    logger.info(".....Fetched books isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
	    logger.info(".... Fetching books isbn-1234");
	    logger.info(".....Fetched books isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
	    logger.info(".... Fetching books isbn-1234");
	    logger.info(".....Fetched books isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
	    logger.info(".... Fetching books Spring book");
	    Book b = bookRepository.getByTitle("isbn-1234");
	    logger.info(".....Fetched books Spring book -->" + b);
	    System.out.println(cache.getName());
	    ValueWrapper s = cache.get("isbn-4567");
	   System.out.println(s.get().toString());
	    
	    System.out.println();
	  }

	
	

}
