package com.ak.poc.spring.cache.jcache.ri.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ak.poc.spring.cache.jcache.ri.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository<String, Book> {

	private static final String SELECT_BOOK = "select id, isbn, title from book";

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Book findOneByIsbnFromDB(String isbn) {
		System.out.println("getting book from DB via findOneByIsbnFromDB");
		return jdbcTemplate.queryForObject(SELECT_BOOK + " where isbn=?", new BookRowMapper(), isbn);
	}

	public Book findOneByTitleFromDB(String title) {
		System.out.println("getting book from DB via findOneByTitleFromDB");
		return jdbcTemplate.queryForObject(SELECT_BOOK + " where title=?", new BookRowMapper(), title);
	}

	public Book saveOrUpdateBookInDB(Book book) {
		System.out.println("saving/updating book into DB via saveBookInDB");
		int id = book.getId();
		if (id == 0) {
			int bookId = insertBookInDB(book);
			return new Book(bookId, book.getIsbn(), book.getTitle());
		} else {
			return updateBookInDB(book);
		}
	}

	private int insertBookInDB(Book book) {
		System.out.println("saving book into DB via insertBookAndReturnIdInDB");
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("book");
		jdbcInsert.setGeneratedKeyName("id");
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("isbn", book.getIsbn());
		args.put("title", book.getTitle());
		int id = jdbcInsert.executeAndReturnKey(args).intValue();
		return id;
	}

	public Book updateBookInDB(Book book) {
		System.out.println("updating book into DB via updateBookInDB");
		jdbcTemplate.update("update book set isbn=?, title=? where id=?", book.getIsbn(), book.getTitle(),
				book.getId());
		return book;
	}

	public void deleteBookFromDB(String isbn) {
		System.out.println("deleting book into DB via deleteBookFromDB");
		jdbcTemplate.update("delete from book where isbn=?", isbn);
	}

	public List<Book> findAllBooksFromDB() {
		System.out.println("getting all book from DB via findAllBooksFromDB");
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

}
