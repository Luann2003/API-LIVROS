package com.apilivros.apilivros.dto;

import java.time.Instant;

import com.apilivros.apilivros.entities.Rent;

public class RentDTO {
	
	private Long id;
	private Double price;
	private Instant initDate;
	private Instant devolutionDate;
	private boolean devolution;
	
	public RentDTO() {
	}

	public RentDTO(Long id, Double price, Instant initDate, Instant devolutionDate, boolean devolution) {
		this.id = id;
		this.price = price;
		this.initDate = initDate;
		this.devolutionDate = devolutionDate;
		this.devolution = devolution;
	}

	public RentDTO(Rent entity) {
		id = entity.getId();
		price = entity.getPrice();
		initDate = entity.getInitDate();
		devolutionDate = entity.getDevolutionDate();
		devolution = entity.isDevolution();
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

	public boolean isDevolution() {
		return devolution;
	}
	
}
