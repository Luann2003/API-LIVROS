package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Book;

public class BookDTO {

	private Long id;
	private String title;
	private Long isbn;
	private Long yearPublication;
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
<<<<<<< HEAD
}
=======
}
>>>>>>> f5e101b26cd60bd69f757b55d8199696f250aa05
