package com.apilivros.apilivros.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apilivros.apilivros.dto.RentDTO;
import com.apilivros.apilivros.services.RentService;

@RestController
@RequestMapping(value = "/rent")
public class RentController {

	@Autowired
	private RentService service;
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<Page<RentDTO>> findAll(
			 @RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){
		Page<RentDTO> result = service.findAll(name, pageable);
		return ResponseEntity.ok().body(result);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<RentDTO> findById (@PathVariable Long id){
		RentDTO rentDTO = service.findById(id);
		return ResponseEntity.ok(rentDTO);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<RentDTO> insert (@RequestBody RentDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
	 return ResponseEntity.created(uri).body(dto);
		
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<RentDTO> update (@PathVariable Long id, @RequestBody RentDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
	@PutMapping(value = "/expected/{id}")
	public ResponseEntity<RentDTO> updateExpectedReturnDate (@PathVariable Long id, @RequestBody RentDTO dto){
		dto = service.updateExpectedReturnDate(id, dto);
		return ResponseEntity.ok(dto);
	}
}
