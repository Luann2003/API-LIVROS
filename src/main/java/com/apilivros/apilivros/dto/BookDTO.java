package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Book;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookDTO {

	@Schema(description = "Database generated book ID")
	private Long id;
	@Schema(description = "Book title")
	private String title;
	@Schema(description = "Book isbn")
	private Long isbn;
	@Schema(description = "Book year published")
	private Long yearPublication;
	@Schema(description = "rented book")
	private boolean rent;

	public BookDTO() {
	}

	public BookDTO(Long id, String title, Long isbn, Long yearPublication, boolean rent) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.yearPublication = yearPublication;
		this.rent = rent;
	}

	public BookDTO(Book entity) {
		id = entity.getId();
		title = entity.getTitle();
		isbn = entity.getIsbn();
		yearPublication = entity.getYearPublication();
		rent = entity.isRent();
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

	public boolean isRent() {
		return rent;
	}
}

