package com.apilivros.apilivros.controller;

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
import org.springframework.transaction.annotation.Transactional;

import com.apilivros.apilivros.dto.RentDTO;
import com.apilivros.apilivros.entities.Book;
import com.apilivros.apilivros.entities.Rent;
import com.apilivros.apilivros.entities.User;
import com.apilivros.apilivros.factory.RentFactory;
import com.apilivros.apilivros.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RentControllerTests {

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
	public void findAllShouldReturnBooksAndUserAdmin() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		ResultActions result = mockMvc.perform(get("/rent").header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void findAllShouldReturnBooksAndUserMember() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		ResultActions result = mockMvc.perform(get("/rent").header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void findAllShouldReturnBooksUnauthorizedNotLogged() throws Exception {

		String accessToken = " ";

		ResultActions result = mockMvc.perform(get("/rent").header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}

	@Test
	public void findByIdShouldReturnBooksIfLoggedIsOk() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		ResultActions result = mockMvc.perform(get("/rent/{id}", existingId).header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnUnauthorizedIsNotLogged() throws Exception {

		String accessToken = "";

		ResultActions result = mockMvc.perform(get("/rent/{id}", existingId).header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void findByIdShouldReturnNotFound() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		ResultActions result = mockMvc.perform(get("/rent/{id}", nonExistingId).header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertShouldReturnCreated() throws Exception {
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		Rent rent = new Rent();
		
		
		User user = new User();
		user.setId(4L);
		rent.setUser(user);
		
		Book book = new Book();
		book.setId(8L);
		rent.setBook(book);
		
		
		String jsonBody = objectMapper.writeValueAsString(rent);

		ResultActions result = mockMvc
				.perform(post("/rent").content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
	}
	
	@Test
	public void updateBookShouldReturnIsOk() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		RentDTO rentDTO = RentFactory.createRentDTO();

		String jsonBody = objectMapper.writeValueAsString(rentDTO);

		ResultActions result = mockMvc.perform(
				put("/rent/{id}", existingId).content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void updateBookShouldReturnisForbbiden() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		RentDTO rentDTO = RentFactory.createRentDTO();

		String jsonBody = objectMapper.writeValueAsString(rentDTO);

		ResultActions result = mockMvc.perform(
				put("/rent/{id}", existingId).content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void updateBookShouldReturnisUnauthorized() throws Exception {

		String accessToken = "" ;

		RentDTO rentDTO = RentFactory.createRentDTO();

		String jsonBody = objectMapper.writeValueAsString(rentDTO);

		ResultActions result = mockMvc.perform(
				put("/rent/{id}", existingId).content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void updateBookShouldReturnisNotFound() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		RentDTO rentDTO = RentFactory.createRentDTO();

		String jsonBody = objectMapper.writeValueAsString(rentDTO);

		ResultActions result = mockMvc.perform(
				put("/rent/{id}", nonExistingId).content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void updateBookRenovaçãoReturnIsOk() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		RentDTO rentDTO = RentFactory.createRentDTO();

		String jsonBody = objectMapper.writeValueAsString(rentDTO);

		ResultActions result = mockMvc.perform(
				put("/rent/expected/{id}", existingId).content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void updateBookRenovaçãoisReturnIsOk() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		RentDTO rentDTO = RentFactory.createRentDTO();

		String jsonBody = objectMapper.writeValueAsString(rentDTO);

		ResultActions result = mockMvc.perform(
				put("/rent/expected/{id}", existingId).content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void updateBookRenovaçãoShouldReturnisUnauthorized() throws Exception {

		String accessToken = "" ;

		RentDTO rentDTO = RentFactory.createRentDTO();

		String jsonBody = objectMapper.writeValueAsString(rentDTO);

		ResultActions result = mockMvc.perform(
				put("/rent/expected/{id}", existingId).content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}
}
