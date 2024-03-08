package com.apilivros.apilivros.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.apilivros.apilivros.dto.BookAuthorDTO;
import com.apilivros.apilivros.entities.Author;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.entities.Publisher;
import com.apilivros.apilivros.factory.AuthorFactory;
import com.apilivros.apilivros.factory.BookFactory;
import com.apilivros.apilivros.factory.PublisherFactory;
import com.apilivros.apilivros.repositories.AuthorRepository;
import com.apilivros.apilivros.repositories.BookRepository;
import com.apilivros.apilivros.repositories.PublisherRepository;
import com.apilivros.apilivros.services.exceptions.DatabaseException;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class BookServiceTests {

	@InjectMocks
	private BookService service;
	
	@Mock
	private BookRepository repository;
	
	@Mock
	private PublisherRepository publisherRepository;
	
	@Mock
	private AuthorRepository authorRepository;
	
	private Long existingId, nonExistingId, dependentId;
	
	private PageImpl<Book> page;
	private Book book;
	private BookAuthorDTO bookAuthorDTO;
	
	private Author author;
	
	private Publisher publisher;

	
	String name;
	
	@BeforeEach
	void setup() {
		
		existingId = 1L;
		nonExistingId = 100L;
		dependentId = 2L;
		
		book = BookFactory.createdBook();
		bookAuthorDTO = BookFactory.createdBookAuthorDTO();
		
		author = AuthorFactory.createdAuthor();
		
		publisher = PublisherFactory.createdPublisher();
		
		page = new PageImpl<>(List.of(book));
		
		name = "testTitle";
		
		Mockito.when(repository.searchByName(eq(name), any())).thenReturn(page);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(book));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(book);
		Mockito.when(authorRepository.getReferenceById(existingId)).thenReturn(author);
		Mockito.when(publisherRepository.getReferenceById(existingId)).thenReturn(publisher);
		
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		Mockito.when(authorRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		Mockito.when(publisherRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		
		Mockito.when(repository.save(any())).thenReturn(book);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
		Mockito.when(repository.existsById(dependentId)).thenReturn(true);	
		
	}
	
	@Test
	public void findAllShouldReturnPageBookAuthorDTO() {
		
		Pageable pageable = PageRequest.of(0, 5);
		
		Page<BookAuthorDTO> result = service.findAll(name, pageable);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.iterator().next().getTitle(), name);
	}
	
	@Test
	public void findByIdShouldReturnWhenIdExists() {
		
		BookAuthorDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
		
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			BookAuthorDTO result = service.findById(nonExistingId);
		});
	}
	
	@Test
	public void insertShouldReturnBookAuthorDTO(){
		
		BookService serviceSpy = Mockito.spy(service);
		BookAuthorDTO result = serviceSpy.insert(bookAuthorDTO);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
		
	}
	
	@Test
	public void insertShouldReturnDatabaseExceptionWhenAuthorOrPublisherIdNoExists() {
		
		BookService serviceSpy = Mockito.spy(service);
		Mockito.when(repository.save(any())).thenThrow(EntityNotFoundException.class);
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			@SuppressWarnings("unused")
			BookAuthorDTO result = serviceSpy.insert(bookAuthorDTO);
		});
	}
	
	@Test
	public void updateShouldReturnBookAuthorDTOWhenIdExists() {

		BookService serviceSpy = Mockito.spy(service);
		BookAuthorDTO result = serviceSpy.update(bookAuthorDTO,existingId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		BookService serviceSpy = Mockito.spy(service);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			BookAuthorDTO result = serviceSpy.update(bookAuthorDTO,nonExistingId);
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		Mockito.verify(repository, times(1)).deleteById(existingId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		Mockito.verify(repository, times(1)).deleteById(dependentId);
	}
	
}
