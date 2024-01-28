package com.apilivros.apilivros.dto;

import java.util.ArrayList;
import java.util.List;

import com.apilivros.apilivros.entities.Author;
import com.apilivros.apilivros.entities.Book;

public class AuthorDTO {
	
	private Long id;
	private String name;
	
	private Long bookId;
	
	private List<BookDTO> books = new ArrayList<>();
	
	public AuthorDTO() {
	}

	public AuthorDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public AuthorDTO(Author entity) {
		id = entity.getId();
		name = entity.getName();
		
		
		for(Book book: entity.getBooks()) {
			books.add(new BookDTO(book));
			bookId = book.getId();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BookDTO> getBooks() {
		return books;
	}

	public Long getBookId() {
		return bookId;
	}
}
