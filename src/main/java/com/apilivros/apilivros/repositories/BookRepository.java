package com.apilivros.apilivros.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apilivros.apilivros.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	
	@Query("SELECT b " +
            "FROM Book b " +
            "JOIN FETCH b.author " +
            "JOIN FETCH b.publisher " +
            "WHERE b.title LIKE %:name%")
    Page<Book> searchByName(@Param("name") String name, Pageable pageable);
}
