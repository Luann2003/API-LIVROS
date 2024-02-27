package com.apilivros.apilivros.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apilivros.apilivros.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

	@Query("SELECT DISTINCT a " +
		       "FROM Author a " +
		       "JOIN FETCH a.books b " +
		       "WHERE a.name LIKE %:name%")
		Page<Author> searchByName(@Param("name") String name, Pageable pageable);
}
