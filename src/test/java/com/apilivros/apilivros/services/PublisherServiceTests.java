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

import com.apilivros.apilivros.dto.PublisherDTO;
import com.apilivros.apilivros.entities.Publisher;
import com.apilivros.apilivros.factory.PublisherFactory;
import com.apilivros.apilivros.repositories.PublisherRepository;
import com.apilivros.apilivros.services.exceptions.DatabaseException;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class PublisherServiceTests {
	@InjectMocks
	private PublisherService service;
	
	@Mock
	private PublisherRepository repository;
	
	private Long existingId, nonExistingId, dependentId;
	
	private Publisher author;
	private PublisherDTO dto;
	
	private PageImpl<Publisher> page;
	
	String name;
	
	@BeforeEach
	void setup() throws Exception {
		
		existingId = 1L;
		nonExistingId = 100L;
		dependentId = 2L;
		
		author = PublisherFactory.createdPublisher();
		dto = PublisherFactory.createdPublisherDTO();
		page = new PageImpl<>(List.of(author));
		
		name = "teste50";
		
		Mockito.when(repository.searchByName(eq(name), any())).thenReturn(page);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(author));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.save(any())).thenReturn(author);
		
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(author);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
		Mockito.when(repository.existsById(dependentId)).thenReturn(true);
	}
	
	@Test
	public void findAllShouldReturnPagedPublisherDTO() {
		
		Pageable pageable = PageRequest.of(0, 5);
		
		Page<PublisherDTO> result = service.findAll(name, pageable);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.iterator().next().getName(), name);
	}
	
	@Test
	public void findByIdShoulReturnPublisherDTOWhenIdExisting() {
		
		PublisherDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
		
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			PublisherDTO result = service.findById(nonExistingId);
		});
	}
	
	@Test
	public void insertShouldReturnPublisherDTO() {

		PublisherService serviceSpy = Mockito.spy(service);
		PublisherDTO result = serviceSpy.insert(dto);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void updateShouldReturnPublisherDTOWhenIdExists() {

		PublisherService serviceSpy = Mockito.spy(service);
		PublisherDTO result = serviceSpy.update(dto, existingId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		PublisherService serviceSpy = Mockito.spy(service);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			PublisherDTO result = serviceSpy.update(dto, nonExistingId);
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