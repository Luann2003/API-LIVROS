package com.apilivros.apilivros.dto;

import java.time.Instant;

import com.apilivros.apilivros.entities.Rent;

public class RentDTO {

	private Long id;
	private Double price;
	private Instant initDate;
	private Instant devolutionDate;
	private Instant expectedReturnDate;

	private UserDTO user;

	private BookDTO book;
	
	private boolean devolutionRent;

	public RentDTO() {
	}

	
	public RentDTO(Long id, Double price, Instant initDate, Instant devolutionDate, UserDTO user, BookDTO book,
			boolean devolution, Instant expectedReturnDate) {
		this.id = id;
		this.price = price;
		this.initDate = initDate;
		this.devolutionDate = devolutionDate;
		this.user = user;
		this.book = book;
		this.devolutionRent = devolution;
		this.expectedReturnDate = expectedReturnDate;
	}


	public RentDTO(Rent entity) {
		id = entity.getId();
		price = entity.getPrice();
		initDate = entity.getInitDate();
		devolutionDate = entity.getDevolutionDate();
		devolutionRent = entity.isDevolution();
		expectedReturnDate = entity.getExpectedReturnDate();
		user = new UserDTO(entity.getUser());
		book = new BookDTO(entity.getBook());
	}

	public Long getId() {
		return id;
	}

	public Double getPrice() {
		return price;
	}

	public Instant getInitDate() {
		return initDate;
	}

	public Instant getDevolutionDate() {
		return devolutionDate;
	}

	public UserDTO getUser() {
		return user;
	}

	public BookDTO getBook() {
		return book;
	}
	
	public Instant getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public boolean isDevolutionRent() {
		return devolutionRent;
	}

}