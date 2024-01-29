package com.apilivros.apilivros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apilivros.apilivros.dto.AuthorDTO;
import com.apilivros.apilivros.services.AuthorService;

@RestController
@RequestMapping(value = "/author")
public class AuthorController {
	
	@Autowired
	private AuthorService service;
	
	@GetMapping
	public ResponseEntity<List<AuthorDTO>> findAll(){
		List<AuthorDTO> result = service.findAll();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AuthorDTO> findById (@PathVariable Long id){
		AuthorDTO bookDTO = service.findById(id);
		return ResponseEntity.ok(bookDTO);
		
	}
}
