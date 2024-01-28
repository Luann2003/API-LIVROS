package com.apilivros.apilivros.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.BookAuthorDTO;
import com.apilivros.apilivros.dto.BookDTO;
import com.apilivros.apilivros.entities.Author;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.entities.Publisher;
import com.apilivros.apilivros.repositories.AuthorRepository;
import com.apilivros.apilivros.repositories.BookRepository;
import com.apilivros.apilivros.repositories.PublisherRepository;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

@Service
public class BookService {
	
	@Autowired
	private BookRepository repository;	
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
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
	public BookAuthorDTO insert(BookAuthorDTO dto) {
		Book entity = new Book();
		
		copyDtoToEntity(dto, entity);
		
		Author author = new Author();
		author.setId(dto.getAuthor().getId());
		author.setName(dto.getAuthor().getName());
		author = authorRepository.save(author);
		Publisher publisher = new Publisher();
		publisher.setName(dto.getPublisher().getName());
		publisher.setName(dto.getPublisher().getName());
		publisher = publisherRepository.save(publisher);
		
		
		entity.setAuthor(author);
		entity.setPublisher(publisher);
		entity = repository.save(entity); 
		
		return new BookAuthorDTO(entity);
	}
	
	private void copyDtoToEntity(BookAuthorDTO dto, Book entity) {
		entity.setIsbn(dto.getIsbn());
		entity.setTitle(dto.getTitle());
		entity.setYearPublication(dto.getYearPublication());
			
	}

}
