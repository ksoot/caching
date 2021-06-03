package com.ak.poc.spring.cache.ehCache.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ak.poc.spring.cache.ehCache.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	@Cacheable(cacheNames = "books_cache")
	public Book getByIsbn(String isbn) {
		return findOneByIsbn(isbn);
	}

	@Cacheable(cacheNames = "books_cache", key = "#title")
	public Book getByTitle(String title) {
		return findOneByTitle(title);
	}

	public Book findOneByIsbn(String isbn) {
		System.out.println("getting book from DB");
		return jdbcTemplate.queryForObject(SELECT_BOOK + " where isbn=?", new BookRowMapper(), isbn);
	}

	public Book findOneByTitle(String title) {
		System.out.println("getting book from DB");
		return jdbcTemplate.queryForObject(SELECT_BOOK + " where title=?", new BookRowMapper(), title);
	}

	public Book save(Book book) {
		int id = book.getId();
		if (id == 0) {
			int bookId = insertBookAndReturnId(book);
			return new Book(bookId, book.getIsbn(), book.getTitle());
		} else {
			jdbcTemplate.update("update book set isbn=?, title=? where id=?", book.getIsbn(), book.getTitle(), id);
		}
		return book;
	}

	private int insertBookAndReturnId(Book book) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("book");
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("isbn", book.getIsbn());
		args.put("title", book.getTitle());
		int spitterId = jdbcInsert.executeAndReturnKey(args).intValue();
		return spitterId;
	}

	public List<Book> findAllBooks() {
		return jdbcTemplate.query(SELECT_BOOK + " order by id", new BookRowMapper());
	}

	private static final class BookRowMapper implements RowMapper<Book> {
		public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
			int id = rs.getInt("id");
			String isbn = rs.getString("isbn");
			String title = rs.getString("title");
			return new Book(id, isbn, title);
		}
	}


	private static final String SELECT_BOOK = "select id, isbn, title from book";

}
