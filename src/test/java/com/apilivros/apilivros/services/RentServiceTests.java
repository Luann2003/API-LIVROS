package com.apilivros.apilivros.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.apilivros.apilivros.dto.BookAuthorDTO;
import com.apilivros.apilivros.dto.RentDTO;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.entities.Rent;
import com.apilivros.apilivros.entities.User;
import com.apilivros.apilivros.factory.BookFactory;
import com.apilivros.apilivros.factory.RentFactory;
import com.apilivros.apilivros.factory.UserFactory;
import com.apilivros.apilivros.repositories.BookRepository;
import com.apilivros.apilivros.repositories.RentRepository;
import com.apilivros.apilivros.repositories.UserRepository;
import com.apilivros.apilivros.services.exceptions.DatabaseException;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class RentServiceTests {

	@InjectMocks
	private RentService service;

	@Mock
	private RentRepository repository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private BookRepository bookRepository;

	private Long existingId, nonExistingId, dependentId;

	private PageImpl<Rent> page;

	private Book book;
	private BookAuthorDTO bookAuthorDTO;

	private User user;

	private Rent rent;
	private RentDTO rentDTO;

	String name;

	@BeforeEach
	void setup() {

		existingId = 1L;
		nonExistingId = 100L;
		dependentId = 2L;

		book = BookFactory.createdBook();
		bookAuthorDTO = BookFactory.createdBookAuthorDTO();

		rent = RentFactory.createRent();
		rentDTO = RentFactory.createRentDTO();

		user = UserFactory.createUser();

		page = new PageImpl<>(List.of(rent));

		name = "test";

		Mockito.when(repository.searchByName(eq(name), any())).thenReturn(page);

		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(rent));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.when(bookRepository.getReferenceById(existingId)).thenReturn(book);
		Mockito.when(bookRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		book.setRent(false);

		Mockito.when(userRepository.getReferenceById(existingId)).thenReturn(user);
		Mockito.when(userRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(repository.getReferenceById(existingId)).thenReturn(rent);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);

		Mockito.when(repository.save(any())).thenReturn(rent);

	}

	@Test
	public void findAllShouldReturnPagedRentDTO() {

		Pageable pageable = PageRequest.of(0, 5);

		Page<RentDTO> result = service.findAll(name, pageable);
		Assertions.assertNotNull(result);
	}

	@Test
	public void findByIdShouldReturnRentDTOWhenIdExists() {

		RentDTO result = service.findById(existingId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			RentDTO result = service.findById(nonExistingId);
		});
	}

	@Test
	public void insertShouldReturnRentDTO() {

		RentService serviceSpy = Mockito.spy(service);
		RentDTO result = serviceSpy.insert(rentDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}

	@Test
	public void insertShouldThrowNewDatabaseExceptioWhenRentIsTrue() {

		RentService serviceSpy = Mockito.spy(service);

		rent.setDevolution(false);
		user.getRents().add(rent);

		Assertions.assertThrows(DatabaseException.class, () -> {
			@SuppressWarnings("unused")
			RentDTO result = serviceSpy.insert(rentDTO);
		});

	}

	@Test
	public void insertShouldThrowNewDatabaseExceptioWhenBookIsTrue() {
		book.setRent(true);
		RentService serviceSpy = Mockito.spy(service);

		Assertions.assertThrows(DatabaseException.class, () -> {
			@SuppressWarnings("unused")
			RentDTO result = serviceSpy.insert(rentDTO);
		});
	}

	@Test
	public void insertShouldThrowNewDatabaseExceptioWhenNotFound() {
			
		RentService serviceSpy = Mockito.spy(service);
		Mockito.when(repository.save(any())).thenThrow(EntityNotFoundException.class);
		
		Assertions.assertThrows(DatabaseException.class, () -> {
			@SuppressWarnings("unused")
			RentDTO result = serviceSpy.insert(rentDTO);
		});
		
	}

	@Test
	public void updateShouldReturnRentDTO() {
		rent.setInitDate(Instant.parse("2024-03-05T23:59:59Z"));
		rent.setDevolutionDate(Instant.parse("2024-03-06T23:59:59Z"));
		rent.setExpectedReturnDate(Instant.now());
		RentService serviceSpy = Mockito.spy(service);
		RentDTO result = serviceSpy.update(existingId, rentDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}

	@Test
	public void updateShouldThrowNewDatabaseExceptioWhenDevolutionDateIsAffterInitDate() {
		rent.setInitDate(Instant.now());
		rent.setDevolutionDate(Instant.parse("2024-03-05T23:59:59Z"));
		RentService serviceSpy = Mockito.spy(service);

		Assertions.assertThrows(DatabaseException.class, () -> {
			@SuppressWarnings("unused")
			RentDTO result = serviceSpy.update(existingId, rentDTO);
		});
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenRentIsNull() {

		RentService serviceSpy = Mockito.spy(service);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			RentDTO result = serviceSpy.update(nonExistingId, rentDTO);
		});
	}

	@Test
	public void updateExpectedReturnDateShouldReturnRentDTO() {

		Instant initialExpectedReturnDate = Instant.now();
		rent.setExpectedReturnDate(initialExpectedReturnDate);

		RentService serviceSpy = Mockito.spy(service);

		RentDTO result = serviceSpy.updateExpectedReturnDate(existingId, rentDTO);

		Assertions.assertNotNull(result);
	}
}
