package com.apilivros.apilivros.services;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.RentDTO;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.entities.Rent;
import com.apilivros.apilivros.entities.User;
import com.apilivros.apilivros.repositories.BookRepository;
import com.apilivros.apilivros.repositories.RentRepository;
import com.apilivros.apilivros.repositories.UserRepository;
import com.apilivros.apilivros.services.exceptions.DatabaseException;
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
	public Page<RentDTO> findAll(String name, Pageable pageable) {
		Page<Rent> list = repository.searchByName(name, pageable);
		return list.map(x -> new RentDTO(x));
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
		Book book = bookRepository.getReferenceById(dto.getBook().getId());

		try {

			user.setId(dto.getUser().getId());
			if (user.getRents().stream().anyMatch(rent -> !rent.isDevolution())) {
				throw new DatabaseException("O usuário já possui um livro alugado");
			}

			Instant now = Instant.now();
			Instant expectedReturnDate = now.plus(Duration.ofDays(7));
			if (!book.isRent()) {
				book.setId(dto.getBook().getId());
				book.setRent(true);
				entity.setBook(book);
				entity.setExpectedReturnDate(expectedReturnDate);
				copyDtoToEntity(dto, entity);
			} else {
				throw new DatabaseException("O livro ja esta alugado");
			}

			entity.setUser(user);
			entity = repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new DatabaseException("User ou Book não encontrado");
		}
		return new RentDTO(entity);
	}

	@Transactional
	public RentDTO update(Long id, RentDTO dto) {
		try {
			Rent entity = repository.getReferenceById(id);
			Book book = entity.getBook();
			book.setRent(false);

			entity.setDevolution(true);

			if (dto.getDevolutionDate().isAfter(entity.getInitDate())) {
				entity.setDevolutionDate(dto.getDevolutionDate());
			} else {
				throw new DatabaseException("Favor entrar com uma data posterior a data inicial");
			}

			// Podemos utilizar o Instant para obtermos a data atual da devolução

			if (dto.getDevolutionDate().isAfter(entity.getExpectedReturnDate())) {
				long daysLate = ChronoUnit.DAYS.between(entity.getExpectedReturnDate(), dto.getDevolutionDate());
				double fineAmount = daysLate * 5.0; // R$ 5 por dia de atraso
				entity.applyFine(fineAmount); // Aplica a multa ao preço do aluguel
			}

			entity = repository.save(entity);
			return new RentDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Recurso não encontrado");
		}
	}

	@Transactional
	public RentDTO updateExpectedReturnDate(Long id, RentDTO dto) {
		Rent entity = repository.getReferenceById(id);

		Instant expectedReturnDate = entity.getExpectedReturnDate().plus(Duration.ofDays(7));
		entity.setExpectedReturnDate(expectedReturnDate);

		entity = repository.save(entity);

		return new RentDTO(entity);

	}

	private void copyDtoToEntity(RentDTO dto, Rent entity) {
		entity.setInitDate(Instant.now());

	}

}