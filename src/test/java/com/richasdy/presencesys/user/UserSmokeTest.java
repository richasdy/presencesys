package com.richasdy.presencesys.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.richasdy.presencesys.user.UserController;
import com.richasdy.presencesys.user.UserRepository;
import com.richasdy.presencesys.user.UserService;
import com.richasdy.presencesys.user.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSmokeTest {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserController userController;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	
	
	
	@Test
	public void contexLoadsUserRepository() throws Exception {
		assertThat(userRepository).isNotNull();
	}

	@Test
	public void contexLoadsUserController() throws Exception {
		assertThat(userController).isNotNull();
	}

	@Test
	public void contexLoadsUserService() throws Exception {
		assertThat(userService).isNotNull();
	}
	
	@Test
	public void contexLoadsUserServiceImpl() throws Exception {
		assertThat(userServiceImpl).isNotNull();
	}

}
