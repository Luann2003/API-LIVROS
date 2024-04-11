package com.apilivros.apilivros.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apilivros.apilivros.dto.AuthorDTO;
import com.apilivros.apilivros.services.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/author")
@Tag(name = "Author", description = "Controller for Author")
public class AuthorController {
	
	@Autowired
	private AuthorService service;
	
	@Operation(
		    description = "Get all Authors",
		    summary = "Get all Authors",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		    }
		)
	@GetMapping(produces = "application/json")
	public ResponseEntity<Page<AuthorDTO>> findAll(
			 @RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){
		Page<AuthorDTO> result = service.findAll(name, pageable);
		return ResponseEntity.ok().body(result);
	}
	
	@Operation(
		    description = "Get Author by id",
		    summary = "Get Author by id",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Not Found", responseCode = "404"),
		    }
		)
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<AuthorDTO> findById (@PathVariable Long id){
		AuthorDTO bookDTO = service.findById(id);
		return ResponseEntity.ok(bookDTO);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Create a new Author",
		    summary = "Create a new Author",
		    responses = {
		         @ApiResponse(description = "Created", responseCode = "201"),
		         @ApiResponse(description = "Bad Request", responseCode = "400"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
		    }
		)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(produces = "application/json")
	public ResponseEntity<AuthorDTO> insert (@RequestBody AuthorDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
	 return ResponseEntity.created(uri).body(dto);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Update Author by Id",
		    summary = "Update Author by Id",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Bad Request", responseCode = "400"),
		         @ApiResponse(description = "Not Found", responseCode = "404"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
		    }
		)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<AuthorDTO> update (@PathVariable Long id, @RequestBody AuthorDTO dto){
		dto = service.update(dto, id);
		return ResponseEntity.ok(dto);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Delete Author by Id",
		    summary = "Delete Author by Id",
		    responses = {
		         @ApiResponse(description = "Sucess", responseCode = "204"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Not Found", responseCode = "404"),
		         @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
		    }
		)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<AuthorDTO> delete (@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
