package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Book;

public class BookDTO {
	
	private Long id;
	private String title;
	private Long isbn;
	private Long yearPublication;
	
	

	public BookDTO() {
	}

	public BookDTO(Long id, String title, Long isbn, Long yearPublication) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.yearPublication = yearPublication;

	}

	public BookDTO(Book entity) {
		id = entity.getId();
		title = entity.getTitle();
		isbn = entity.getIsbn();
		yearPublication = entity.getYearPublication();

	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Long getIsbn() {
		return isbn;
	}

	public Long getYearPublication() {
		return yearPublication;
	}
}
