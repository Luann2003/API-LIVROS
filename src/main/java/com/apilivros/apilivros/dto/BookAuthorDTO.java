package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Book;

import io.swagger.v3.oas.annotations.media.Schema;

public class BookAuthorDTO {
	
	@Schema(description = "Database generated BookAuthor ID")
	private Long id;
	@Schema(description = "BookAuthor title")
	private String title;
	@Schema(description = "BookAuthor isbn")
	private Long isbn;
	@Schema(description = "BookAuthor year published")
	private Long yearPublication;
	@Schema(description = "rented BookAuthor")
	private boolean rent;
	
	@Schema(description = "Author")
	private AuthorNoBooksDTO author;
	@Schema(description = "Publisher")
	private PublisherNoBooksDTO publisher;
	
	public BookAuthorDTO() {
	}

	public BookAuthorDTO(Long id, String title, Long isbn, Long yearPublication, boolean rent, AuthorNoBooksDTO author, PublisherNoBooksDTO publisher) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.yearPublication = yearPublication;
		this.rent = rent;
		this.author = author;
		this.publisher = publisher;
	}
	
	public BookAuthorDTO(Book entity) {
		id = entity.getId();
		title = entity.getTitle();
		isbn = entity.getIsbn();
		yearPublication = entity.getYearPublication();
		rent = entity.isRent();
		author = new AuthorNoBooksDTO(entity.getAuthor());
		publisher = new PublisherNoBooksDTO(entity.getPublisher());
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

	public AuthorNoBooksDTO getAuthor() {
		return author;
	}

	public PublisherNoBooksDTO getPublisher() {
		return publisher;
	}

	public boolean isRent() {
		return rent;
	}
	
	
	
}
