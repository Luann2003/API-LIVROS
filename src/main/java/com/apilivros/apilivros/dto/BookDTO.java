package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Author;
import com.apilivros.apilivros.entities.Book;

public class BookDTO {
	
	private Long id;
	private String title;
	private Long isbn;
	private Long yearPublication;
	
	private String publisherDTO;
	
	private Author authorDTO;
	
	public BookDTO() {
	}

	
	
	public BookDTO(Long id, String title, Long isbn, Long yearPublication, String publisherDTO, Author authorDTO) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.yearPublication = yearPublication;
		this.publisherDTO = publisherDTO;
		this.authorDTO = authorDTO;
	}

	public BookDTO(Book entity) {
		id = entity.getId();
		title = entity.getTitle();
		isbn = entity.getIsbn();
		yearPublication = entity.getYearPublication();
		publisherDTO = entity.getPublisher().getName();
		authorDTO = entity.getAuthor();
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

	public String getPublisherDTO() {
		return publisherDTO;
	}
	
	public Author getAuthorDTO() {
		return authorDTO;
	}
}
