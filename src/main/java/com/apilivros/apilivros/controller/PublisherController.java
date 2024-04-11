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

import com.apilivros.apilivros.dto.PublisherDTO;
import com.apilivros.apilivros.services.PublisherService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/publisher")
@Tag(name = "Publisher", description = "Controller for Publisher")
public class PublisherController {
	
	@Autowired
	private PublisherService service;
	
	@Operation(
		    description = "Get all Publishers",
		    summary = "Get all Publishers",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		    }
		)
	@GetMapping(produces = "application/json")
	public ResponseEntity<Page<PublisherDTO>> findAll(
			 @RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){
		Page<PublisherDTO> result = service.findAll(name, pageable);
		return ResponseEntity.ok().body(result);
	}
	
	@Operation(
		    description = "Get Publisher by id",
		    summary = "Get Publisher by id",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Not Found", responseCode = "404"),
		    }
		)
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<PublisherDTO> findById (@PathVariable Long id){
		PublisherDTO bookDTO = service.findById(id);
		return ResponseEntity.ok(bookDTO);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Create a new Publisher",
		    summary = "Create a new Publisher",
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
	public ResponseEntity<PublisherDTO> insert (@RequestBody PublisherDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
	 return ResponseEntity.created(uri).body(dto);
		
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Update Publisher by Id",
		    summary = "Update Publisher by Id",
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
	public ResponseEntity<PublisherDTO> update (@PathVariable Long id, @RequestBody PublisherDTO dto){
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
	public ResponseEntity<PublisherDTO> delete (@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
