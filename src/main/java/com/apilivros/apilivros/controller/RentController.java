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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/rent")
@Tag(name = "Rent", description = "Controller for Rent")
public class RentController {

	@Autowired
	private RentService service;
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Get all rents",
		    summary = "Get all rents",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		    }
		)
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
	@GetMapping(produces = "application/json")
	public ResponseEntity<Page<RentDTO>> findAll(
			 @RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){
		Page<RentDTO> result = service.findAll(name, pageable);
		return ResponseEntity.ok().body(result);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Get rent by id",
		    summary = "Get rent by id",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Not Found", responseCode = "404"),
		    }
		)
	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_ADMIN')")
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<RentDTO> findById (@PathVariable Long id){
		RentDTO rentDTO = service.findById(id);
		return ResponseEntity.ok(rentDTO);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Create a new rent",
		    summary = "Create a new rent",
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
	public ResponseEntity<RentDTO> insert (@RequestBody RentDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
	 return ResponseEntity.created(uri).body(dto);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Update rent by Id",
		    summary = "Update rent by Id",
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
	public ResponseEntity<RentDTO> update (@PathVariable Long id, @RequestBody RentDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok(dto);
	}
	
	@SecurityRequirement(name = "bearerAuth")
	@Operation(
		    description = "Update data expected rent by Id",
		    summary = "Update data expected rent by Id	",
		    responses = {
		         @ApiResponse(description = "Ok", responseCode = "200"),
		         @ApiResponse(description = "Not Found", responseCode = "404"),
		         @ApiResponse(description = "Unauthorized", responseCode = "401"),
		         @ApiResponse(description = "Forbidden", responseCode = "403"),
		         @ApiResponse(description = "Unprocessable Entity", responseCode = "422")
		    }
		)
	@PutMapping(value = "/expected/{id}", produces = "application/json")
	public ResponseEntity<RentDTO> updateExpectedReturnDate (@PathVariable Long id, @RequestBody RentDTO dto){
		dto = service.updateExpectedReturnDate(id, dto);
		return ResponseEntity.ok(dto);
	}
}
