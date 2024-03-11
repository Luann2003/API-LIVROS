package com.apilivros.apilivros.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.BookAuthorDTO;
import com.apilivros.apilivros.factory.BookFactory;
import com.apilivros.apilivros.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private ObjectMapper objectMapper;

	private Long existingId, nonExistingId;

	private String memberUsername, memberPassword;
	private String adminUsername, adminPassword;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
		nonExistingId = 100L;

		adminUsername = "verfute2005@gmail.com";
		adminPassword = "123456";

		memberUsername = "maria@gmail.com";
		memberPassword = "123456";
	}

	@Test
	public void findAllShouldReturnBooks() throws Exception {

		ResultActions result = mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnBookWhenIdExist() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/books/{id}", existingId).contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnBadRequestWhenIdNotExist() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/books/{id}", nonExistingId).contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertShouldReturnCreated() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		BookAuthorDTO bookDTO = BookFactory.createdBookAuthorDTO();

		String jsonBody = objectMapper.writeValueAsString(bookDTO);

		ResultActions result = mockMvc
				.perform(post("/books").content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
	}
	
	@Test
	public void insertShouldReturnForbbidenIfUserLogged() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		BookAuthorDTO bookDTO = BookFactory.createdBookAuthorDTO();

		String jsonBody = objectMapper.writeValueAsString(bookDTO);

		ResultActions result = mockMvc
				.perform(post("/books").content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}

	@Test
	public void insertShouldReturnUnauthorized() throws Exception {
		String accessToken = null;

		BookAuthorDTO bookDTO = BookFactory.createdBookAuthorDTO();


		String jsonBody = objectMapper.writeValueAsString(bookDTO);

		ResultActions result = mockMvc
				.perform(post("/books").content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void updateBookShouldReturnIsOk() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		BookAuthorDTO bookDTO = BookFactory.createdBookAuthorDTO();

		String jsonBody = objectMapper.writeValueAsString(bookDTO);

		ResultActions result = mockMvc.perform(
				put("/books/{id}", existingId).content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void updateBookShouldReturnForbbiden() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		BookAuthorDTO bookDTO = BookFactory.createdBookAuthorDTO();

		String jsonBody = objectMapper.writeValueAsString(bookDTO);

		ResultActions result = mockMvc.perform(
				put("/books/{id}", existingId).content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenNonExistingId() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		ResultActions result = mockMvc
				.perform(delete("/books/{id}", nonExistingId).header("Authorization", "Bearer " + accessToken));

		result.andExpect(status().isNotFound());
	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deleteShouldReturnForbbidenWhenMemberLogged() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		ResultActions result = mockMvc
				.perform(delete("/books/{id}", existingId).header("Authorization", "Bearer " + accessToken));

		result.andExpect(status().isForbidden());
	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS)
	public void deleteShouldReturnNoContent() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		Long idDelete = 5L;

		ResultActions result = mockMvc
				.perform(delete("/books/{id}", idDelete).header("Authorization", "Bearer " + accessToken));

		result.andExpect(status().isNoContent());
	}
}
