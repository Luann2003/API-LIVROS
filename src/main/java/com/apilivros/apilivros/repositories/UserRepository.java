package com.apilivros.apilivros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.apilivros.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	

}
