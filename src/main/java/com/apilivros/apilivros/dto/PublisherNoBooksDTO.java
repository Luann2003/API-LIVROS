package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Publisher;

public class PublisherNoBooksDTO {
	
	private Long id;
	private String name;
	
	public PublisherNoBooksDTO() {
	}

	public PublisherNoBooksDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public PublisherNoBooksDTO(Publisher entity) {
		id = entity.getId();
		name = entity.getName();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
