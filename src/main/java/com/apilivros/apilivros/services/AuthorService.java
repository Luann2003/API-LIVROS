package com.apilivros.apilivros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.AuthorDTO;
import com.apilivros.apilivros.dto.BookDTO;
import com.apilivros.apilivros.entities.Author;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.repositories.AuthorRepository;
import com.apilivros.apilivros.services.exceptions.DatabaseException;
import com.apilivros.apilivros.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository repository;

	@Transactional(readOnly = true)
	public Page<AuthorDTO> findAll(String name, Pageable pageable) {
		Page<Author> list = repository.searchByName(name, pageable);
		return list.map(x -> new AuthorDTO(x));
	}

	@Transactional(readOnly = true)
	public AuthorDTO findById(Long id) {
		Author book = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
		return new AuthorDTO(book);
	}

	@Transactional
	public AuthorDTO insert(AuthorDTO dto) {

		Author entity = new Author();

		copyDtoToEntity(dto, entity);

		entity = repository.save(entity);
		return new AuthorDTO(entity);

	}

	@Transactional
	public AuthorDTO update(AuthorDTO dto, Long id) {
		try {

			Author entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
	
			entity = repository.save(entity);
			return new AuthorDTO(entity);

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
    	}
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

	private void copyDtoToEntity(AuthorDTO dto, Author entity) {
		entity.setName(dto.getName());
		for(Book book: entity.getBooks()) {
			dto.getBooks().add(new BookDTO(book));
			
		}
	}
}
