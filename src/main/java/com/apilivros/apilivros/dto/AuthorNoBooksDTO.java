package com.apilivros.apilivros.dto;

import com.apilivros.apilivros.entities.Author;

public class AuthorNoBooksDTO {
	
	private Long id;
	private String name;
	
	public AuthorNoBooksDTO() {
	}

	public AuthorNoBooksDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public AuthorNoBooksDTO(Author entity) {
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
