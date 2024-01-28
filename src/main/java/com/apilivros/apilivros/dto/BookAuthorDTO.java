package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Book;

public class BookAuthorDTO {
	
	private Long id;
	private String title;
	private Long isbn;
	private Long yearPublication;
	
	private AuthorDTO author;
	
	private PublisherDTO publisher;
	
	public BookAuthorDTO() {
	}

	public BookAuthorDTO(Long id, String title, Long isbn, Long yearPublication,AuthorDTO author, PublisherDTO publisher) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.yearPublication = yearPublication;
		this.author = author;
		this.publisher = publisher;
	}
	
	public BookAuthorDTO(Book entity) {
		id = entity.getId();
		title = entity.getTitle();
		isbn = entity.getIsbn();
		yearPublication = entity.getYearPublication();
		author = new AuthorDTO(entity.getAuthor());
		publisher = new PublisherDTO(entity.getPublisher());
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

	public AuthorDTO getAuthor() {
		return author;
	}

	public PublisherDTO getPublisher() {
		return publisher;
	}
	
}
