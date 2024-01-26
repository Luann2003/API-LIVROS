package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Publisher;

public class PublisherDTO {
	
	private Long id;
	private String name;
	
	public PublisherDTO() {
	}
	
	public PublisherDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public PublisherDTO(Publisher entity) {
		id = entity.getId();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
