package com.apilivros.apilivros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apilivros.apilivros.dto.BookDTO;
import com.apilivros.apilivros.services.BookService;

@RestController
@RequestMapping(value = "/books")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@GetMapping
	public ResponseEntity<List<BookDTO>> findAll(){
		List<BookDTO> result = service.findAll();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<BookDTO> findById (@PathVariable Long id){
		BookDTO bookDTO = service.findById(id);
		return ResponseEntity.ok(bookDTO);
		
	}
	
	
	

}