package com.apilivros.apilivros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.apilivros.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	

}
