package com.apilivros.apilivros.factory;

import java.time.Instant;

import com.apilivros.apilivros.dto.RentDTO;
import com.apilivros.apilivros.entities.Rent;

public class RentFactory {
	
	public static Rent createRent() {
		Rent rent = new Rent(1L, 30.00, Instant.now(), Instant.now(), UserFactory.createUser(), BookFactory.createdBook(), true);
		return rent;
	}
	
	public static RentDTO createRentDTO() {
		RentDTO rentDTO = new RentDTO(1L, 35.00, Instant.now(), Instant.now(), UserFactory.createUserDTO(), BookFactory.createdBookDTO(),
				true, Instant.now());
		return rentDTO;
	}
}
