package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Book;

public class BookDTO {
	
	private Long id;
	private String title;
	private Long isbn;
	private Long yearPublication;
	
	private Long authorId;
	private Long publisherId;
	
	
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
		authorId = entity.getAuthor().getId();
		publisherId = entity.getPublisher().getId();
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

	public Long getAuthorId() {
		return authorId;
	}

	public Long getPublisherId() {
		return publisherId;
	}
	
	

}
