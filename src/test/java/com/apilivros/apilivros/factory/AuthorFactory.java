package com.apilivros.apilivros.factory;

import com.apilivros.apilivros.dto.AuthorDTO;
import com.apilivros.apilivros.dto.AuthorNoBooksDTO;
import com.apilivros.apilivros.entities.Author;

public class AuthorFactory {

	public static Author createdAuthor() {

		Author author = new Author(1L, "teste50");
		return author;
	}

	public static AuthorDTO createdAuthorDTO() {

		AuthorDTO dto = new AuthorDTO(1L, "teste50");
		return dto;
	}
	
	public static AuthorNoBooksDTO createdAuthorNoBooksDTO() {

		AuthorNoBooksDTO dto = new AuthorNoBooksDTO(1L, "teste50");
		return dto;
	}

}
