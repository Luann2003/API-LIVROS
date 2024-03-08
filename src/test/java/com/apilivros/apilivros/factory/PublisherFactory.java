package com.apilivros.apilivros.factory;

import com.apilivros.apilivros.dto.PublisherDTO;
import com.apilivros.apilivros.dto.PublisherNoBooksDTO;
import com.apilivros.apilivros.entities.Publisher;

public class PublisherFactory {

	public static Publisher createdPublisher() {

		Publisher author = new Publisher(1L, "teste50");
		return author;
	}

	public static PublisherDTO createdPublisherDTO() {

		PublisherDTO dto = new PublisherDTO(1L, "teste50");
		return dto;
	}
	
	public static PublisherNoBooksDTO createdPublisherNoBooksDTO() {

		PublisherNoBooksDTO dto = new PublisherNoBooksDTO(1L, "teste50");
		return dto;
	}

}
