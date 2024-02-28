package com.apilivros.apilivros.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apilivros.apilivros.entities.Rent;

public interface RentRepository extends JpaRepository<Rent, Long> {

	@Query("SELECT r FROM Rent r " +
	           "JOIN FETCH r.user u " +
	           "WHERE UPPER(u.name) LIKE UPPER(CONCAT('%', :userName, '%'))")
	    Page<Rent> searchByName(@Param("userName") String userName, Pageable pageable);

	
}
