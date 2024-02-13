package com.apilivros.apilivros.services;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.RentDTO;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.entities.Rent;
import com.apilivros.apilivros.entities.User;
import com.apilivros.apilivros.repositories.BookRepository;
import com.apilivros.apilivros.repositories.RentRepository;
import com.apilivros.apilivros.repositories.UserRepository;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RentService {

	@Autowired
	private RentRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Transactional(readOnly = true)
	public List<RentDTO> findAll() {
		List<Rent> list = repository.findAll();
		return list.stream().map(x -> new RentDTO(x)). toList();
	}

	@Transactional(readOnly = true)
	public RentDTO findById(Long id) {
		Rent rent = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new RentDTO(rent);
	}

	@Transactional
	public RentDTO insert(RentDTO dto) {
		
			Rent entity = new Rent();
			
			User user = userRepository.getReferenceById(dto.getUser().getId());
			user.setId(dto.getUser().getId());
			
			Book book = bookRepository.getReferenceById(dto.getBook().getId());
			
			 if (user.getRents().stream().anyMatch(rent -> !rent.isDevolution())) {
			        throw new RuntimeException("O usuário já possui um livro alugado e não devolvido");
			    }
			 
			copyDtoToEntity(dto, entity);

			if(!book.isRent()) {
				book.setId(dto.getBook().getId());
				book.setRent(true);
				entity.setBook(book);
			}else {
				throw new RuntimeException("O livro ja esta alugado");
			}
			
			
			entity.setUser(user);
			entity = repository.save(entity);

			return new RentDTO(entity);
	}

	@Transactional
	public RentDTO update(RentDTO dto, Long id) {
		try {

			Rent entity = repository.getReferenceById(id);
			Book book = bookRepository.getReferenceById(dto.getBook().getId());
			book.setRent(false);

			entity.setDevolution(true);
			entity.setBook(book);
			entity.setDevolutionDate(Instant.now());
			entity = repository.save(entity);
			return new RentDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

	private void copyDtoToEntity(RentDTO dto, Rent entity) {
		entity.setPrice(dto.getPrice());
		entity.setInitDate(Instant.now());

	}

}