package com.apilivros.apilivros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.PublisherDTO;
import com.apilivros.apilivros.entities.Publisher;
import com.apilivros.apilivros.repositories.PublisherRepository;
import com.apilivros.apilivros.services.exceptions.DatabaseException;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PublisherService {

	@Autowired
	private PublisherRepository repository;

	@Transactional(readOnly = true)
	public Page<PublisherDTO> findAll(String name, Pageable pageable) {
		Page<Publisher> list = repository.searchByName(name, pageable);
		return list.map(x -> new PublisherDTO(x));
	}

	@Transactional(readOnly = true)
	public PublisherDTO findById(Long id) {
		Publisher book = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new PublisherDTO(book);
	}

	@Transactional
	public PublisherDTO insert(PublisherDTO dto) {

		Publisher entity = new Publisher();

		copyDtoToEntity(dto, entity);

		entity = repository.save(entity);
		return new PublisherDTO(entity);
	}

	@Transactional
	public PublisherDTO update(PublisherDTO dto, Long id) {
		try {

			Publisher entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);

			entity = repository.save(entity);
			return new PublisherDTO(entity);

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

	private void copyDtoToEntity(PublisherDTO dto, Publisher entity) {
		entity.setName(dto.getName());
	}
}
