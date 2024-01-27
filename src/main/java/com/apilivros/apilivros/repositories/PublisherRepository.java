package com.apilivros.apilivros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.apilivros.entities.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long>{
	
	

}
