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

import com.apilivros.apilivros.dto.AuthorDTO;
import com.apilivros.apilivros.factory.AuthorFactory;
import com.apilivros.apilivros.utils.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;



@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthorControllerTests {

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
	public void findAllShouldReturnAuthors() throws Exception {

		ResultActions result = mockMvc.perform(get("/author").contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void findByIdShouldReturnAuthorWhenIdExist() throws Exception {
		ResultActions result = mockMvc.perform(get("/author/{id}", existingId).contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void findByIdShouldReturnBadRequestWhenIdNotExist() throws Exception {
		ResultActions result = mockMvc
				.perform(get("/author/{id}", nonExistingId).contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

	@Test
	public void insertShouldReturnCreated() throws Exception{
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		AuthorDTO authorDTO = AuthorFactory.createdAuthorDTO();

		String jsonBody = objectMapper.writeValueAsString(authorDTO);

		ResultActions result = mockMvc
				.perform(post("/author").content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
	}
	
	@Test
	public void insertShouldReturnForbbidenIFMemberLogged() throws Exception{
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		AuthorDTO authorDTO = AuthorFactory.createdAuthorDTO();

		String jsonBody = objectMapper.writeValueAsString(authorDTO);

		ResultActions result = mockMvc
				.perform(post("/author").content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void insertShouldReturnUnauthorized() throws Exception{
		String accessToken = null;

		AuthorDTO authorDTO = AuthorFactory.createdAuthorDTO();

		String jsonBody = objectMapper.writeValueAsString(authorDTO);

		ResultActions result = mockMvc
				.perform(post("/author").content(jsonBody).header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void updateAuthorShouldReturnIsOk() throws Exception {
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		AuthorDTO authorDTO = AuthorFactory.createdAuthorDTO();

		String jsonBody = objectMapper.writeValueAsString(authorDTO);
		
		ResultActions result =
				mockMvc.perform(put("/author/{id}", existingId)
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void updateAuthorShouldReturnForbbiden() throws Exception {
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		AuthorDTO authorDTO = AuthorFactory.createdAuthorDTO();

		String jsonBody = objectMapper.writeValueAsString(authorDTO);
		
		ResultActions result =
				mockMvc.perform(put("/author/{id}", existingId)
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}

	@Test
	public void deleteShouldReturnNotFoundWhenNonExistingId() throws Exception {		
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		ResultActions result =
				mockMvc.perform(delete("/author/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken));
		

		result.andExpect(status().isNotFound());
	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS) 
	public void deleteShouldReturnForbbidenWhenMemberLogged() throws Exception {		
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		
		ResultActions result =
				mockMvc.perform(delete("/author/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken));
				
		result.andExpect(status().isForbidden());
	}

}
