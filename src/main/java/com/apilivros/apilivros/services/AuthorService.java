package com.apilivros.apilivros.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.AuthorDTO;
import com.apilivros.apilivros.entities.Author;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.repositories.AuthorRepository;
import com.apilivros.apilivros.repositories.BookRepository;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

@Service
public class AuthorService {
	
	@Autowired
	private AuthorRepository repository;	
	
	@Autowired
	private BookRepository bookRepository;
	
	@Transactional(readOnly = true)
	public List<AuthorDTO> findAll () {
		List<Author> list = repository.findAll();
		return list.stream().map(x -> new AuthorDTO(x)).toList();
	}
	
	@Transactional(readOnly = true)
	public AuthorDTO findById(Long id){
		Author book = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new AuthorDTO(book);
	}
	
	@Transactional
	public AuthorDTO insert(AuthorDTO dto) {
		Author entity = new Author();
		
		Book book = bookRepository.getReferenceById(dto.getBookId());
		book.setId(dto.getBookId());

		copyDtoToEntity(dto, entity);
		
		entity = repository.save(entity); 
		
		return new AuthorDTO(entity);
	}
	
	private void copyDtoToEntity(AuthorDTO dto, Author entity) {
		entity.setName(dto.getName());
			
	}

}
