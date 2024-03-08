package com.apilivros.apilivros.factory;

import com.apilivros.apilivros.dto.UserDTO;
import com.apilivros.apilivros.entities.User;

public class UserFactory {
	
	public static User createUser() {
		User user = new User(1L, "test@gmail.com", "test", "123456");
		return user;
	}
	
	public static UserDTO createUserDTO() {
		UserDTO userDTO = new UserDTO(1L, "test", "test@gmail.com");
		return userDTO;
	}


}
