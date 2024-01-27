package com.apilivros.apilivros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.apilivros.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{
	
	

}
