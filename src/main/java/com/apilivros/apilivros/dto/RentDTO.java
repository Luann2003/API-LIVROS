package com.apilivros.apilivros.dto;

import java.time.Instant;

import com.apilivros.apilivros.entities.Rent;

public class RentDTO {

	private Long id;
	private Double price;
	private Instant initDate;
	private Instant devolutionDate;

	private UserDTO user;

	private BookDTO book;
<<<<<<< HEAD
	
	private boolean devolutionRent;
=======
>>>>>>> f5e101b26cd60bd69f757b55d8199696f250aa05

	public RentDTO() {
	}

<<<<<<< HEAD
	
	public RentDTO(Long id, Double price, Instant initDate, Instant devolutionDate, UserDTO user, BookDTO book,
			boolean devolution) {
=======
	public RentDTO(Long id, Double price, Instant initDate, Instant devolutionDate, UserDTO user, BookDTO book) {
>>>>>>> f5e101b26cd60bd69f757b55d8199696f250aa05
		this.id = id;
		this.price = price;
		this.initDate = initDate;
		this.devolutionDate = devolutionDate;
		this.user = user;
		this.book = book;
		this.devolutionRent = devolution;
	}


	public RentDTO(Rent entity) {
		id = entity.getId();
		price = entity.getPrice();
		initDate = entity.getInitDate();
		devolutionDate = entity.getDevolutionDate();
<<<<<<< HEAD
		devolutionRent = entity.isDevolution();
=======
>>>>>>> f5e101b26cd60bd69f757b55d8199696f250aa05
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
<<<<<<< HEAD
	public boolean isDevolutionRent() {
		return devolutionRent;
	}

}
=======

}
>>>>>>> f5e101b26cd60bd69f757b55d8199696f250aa05
