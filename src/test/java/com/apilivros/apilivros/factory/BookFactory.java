package com.apilivros.apilivros.factory;

import com.apilivros.apilivros.dto.BookAuthorDTO;
import com.apilivros.apilivros.dto.BookDTO;
import com.apilivros.apilivros.entities.Book;

public class BookFactory {

	public static Book createdBook() {

		Book book = new Book(1L, "testTitle", 123456789L, 1982L, true, PublisherFactory.createdPublisher(),
				AuthorFactory.createdAuthor());
		return book;
	}
	
	public static BookAuthorDTO createdBookAuthorDTO() {

		BookAuthorDTO bookAuthorDTO = new BookAuthorDTO(1L, "testTitle", 123456789L, 1982L, true,
				AuthorFactory.createdAuthorNoBooksDTO(), PublisherFactory.createdPublisherNoBooksDTO());
		return bookAuthorDTO;
	}

	public static BookDTO createdBookDTO() {

		BookDTO bookDTO = new BookDTO(1L, "testTitle", 123456789L, 1982L, true);
		return bookDTO;
	}
}
