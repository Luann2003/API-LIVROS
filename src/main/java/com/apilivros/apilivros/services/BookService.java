package com.apilivros.apilivros.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public BookDTO insert(BookDTO dto) {
		Book entity = new Book();
		
		Author author = authorRepository.getReferenceById(dto.getAuthorId());
		author.setId(dto.getAuthorId());
		Publisher publisher = publisherRepository.getReferenceById(dto.getPublisherId());
		publisher.setId(dto.getId());
		
		copyDtoToEntity(dto, entity);
		entity.setAuthor(author);
		entity.setPublisher(publisher);
		entity = repository.save(entity); 
		
		return new BookDTO(entity);
	}
	
	private void copyDtoToEntity(BookDTO dto, Book entity) {
		entity.setIsbn(dto.getIsbn());
		entity.setTitle(dto.getTitle());
		entity.setYearPublication(dto.getYearPublication());
			
	}

}
