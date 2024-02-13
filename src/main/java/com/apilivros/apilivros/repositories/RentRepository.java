package com.apilivros.apilivros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apilivros.apilivros.entities.Rent;

public interface RentRepository extends JpaRepository<Rent, Long> {

	@Query("SELECT r FROM Rent r WHERE r.user.id = :userId ORDER BY r.id DESC")
	Rent findLastRentByUserId(@Param("userId") Long userId);

}
