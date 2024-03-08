package com.apilivros.apilivros.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.apilivros.apilivros.factory.UserDetailsFactory;
import com.apilivros.apilivros.projections.UserDetailsProjection;
import com.apilivros.apilivros.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserServiceTests {

	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository repository;

	private String existingUsername, nonExistingUsername;

	private List<UserDetailsProjection> userDetails;

	@BeforeEach
	void setup() {

		existingUsername = "alex@gmail.com";
		nonExistingUsername = "user@gmail.com";

		userDetails = UserDetailsFactory.createCustomUser(existingUsername);

		Mockito.when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDetails);
		Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());

	}

	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExist() {
		
		UserDetails result = service.loadUserByUsername(existingUsername);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existingUsername);
		
	}
	
	@Test
	public void loadUserByUsernameShouldUsernameNotFoundExceptionWhenUserDoesNotExists() {

		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExistingUsername);
		});
	}
}
