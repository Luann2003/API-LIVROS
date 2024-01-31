package com.apilivros.apilivros.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apilivros.apilivros.dto.AuthorDTO;
import com.apilivros.apilivros.dto.BookAuthorDTO;
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
	
	@PostMapping
	public ResponseEntity<AuthorDTO> insert (@RequestBody AuthorDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
	 return ResponseEntity.created(uri).body(dto);
		
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<AuthorDTO> update (@PathVariable Long id, @RequestBody AuthorDTO dto){
		dto = service.update(dto, id);
		return ResponseEntity.ok(dto);
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<AuthorDTO> delete (@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
