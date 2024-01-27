package com.apilivros.apilivros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.apilivros.entities.Rent;

public interface RentRepository extends JpaRepository<Rent, Long>{
	
	

}
