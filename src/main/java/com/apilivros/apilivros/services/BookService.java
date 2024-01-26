package com.apilivros.apilivros.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.BookDTO;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.repositories.BookRepository;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;
	
	@Transactional(readOnly = true)
	public List<BookDTO> findAll () {
		List<Book> list = repository.findAll();
		return list.stream().map(x -> new BookDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public BookDTO findById(Long id){
		Book book = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new BookDTO(book);
	}
	
	@Transactional
	private BookDTO insert() {
		
	}
	
	private void copyDtoToEntity(BookDTO dto, Book entity) {
		entity.setIsbn(dto.getIsbn());
		entity.setTitle(dto.getTitle());
		entity.setYearPublication(dto.getYearPublication());
		entity.setAuthor(dto.getAuthorDTO());
	}

}