package com.apilivros.apilivros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.BookAuthorDTO;
import com.apilivros.apilivros.entities.Author;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.entities.Publisher;
import com.apilivros.apilivros.repositories.AuthorRepository;
import com.apilivros.apilivros.repositories.BookRepository;
import com.apilivros.apilivros.repositories.PublisherRepository;
import com.apilivros.apilivros.services.exceptions.DatabaseException;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private PublisherRepository publisherRepository;

	@Transactional(readOnly = true)
	public Page<BookAuthorDTO> findAll(String name, Pageable pageable) {
		Page<Book> list = repository.searchByName(name, pageable);
		return list.map(x -> new BookAuthorDTO(x));
	}

	@Transactional(readOnly = true)
	public BookAuthorDTO findById(Long id) {
		Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new BookAuthorDTO(book);
	}

	@Transactional
	public BookAuthorDTO insert(BookAuthorDTO dto) {
		Book entity = new Book();

		try {
			copyDtoToEntity(dto, entity);
			Author author = authorRepository.getReferenceById(dto.getAuthor().getId());
			author.setId(dto.getAuthor().getId());
			author = authorRepository.save(author);
			Publisher publisher = publisherRepository.getReferenceById(dto.getPublisher().getId());
			publisher.setId(dto.getPublisher().getId());
			publisher = publisherRepository.save(publisher);
			entity.setAuthor(author);
			entity.setPublisher(publisher);
			entity = repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new DatabaseException("Author ou publisher não existente");
		}

		return new BookAuthorDTO(entity);
	}

	@Transactional
	public BookAuthorDTO update(BookAuthorDTO dto, Long id) {
		try {

			Book entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);

			entity = repository.save(entity);
			return new BookAuthorDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Falha de integridade referencial");
		}
	}

	private void copyDtoToEntity(BookAuthorDTO dto, Book entity) {
		entity.setIsbn(dto.getIsbn());
		entity.setTitle(dto.getTitle());
		entity.setYearPublication(dto.getYearPublication());

	}

}
