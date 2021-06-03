package com.ak.poc.spring.cache.jcache.ri.repository;

import java.io.Serializable;
import java.util.List;

public interface BookRepository<K,V> {

	public V findOneByIsbnFromDB(K isbn);
	public V findOneByTitleFromDB(K title);
	public V saveOrUpdateBookInDB(V book) ;
	public void deleteBookFromDB(K isbn);
	public List<V> findAllBooksFromDB() ;


}
