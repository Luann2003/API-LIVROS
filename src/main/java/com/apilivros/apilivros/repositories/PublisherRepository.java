package com.apilivros.apilivros.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apilivros.apilivros.entities.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long>{

	
	@Query("select DISTINCT p " +
			"from Publisher p " + 
			"WHERE p.name LIKE %:name%")
	Page<Publisher> searchByName(@Param("name") String name, Pageable pageable);
	

}
