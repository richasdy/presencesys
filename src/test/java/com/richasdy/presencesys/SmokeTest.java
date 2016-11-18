package com.richasdy.presencesys;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.richasdy.presencesys.api.AccountEndpoint;
import com.richasdy.presencesys.controller.AccountController;
import com.richasdy.presencesys.service.AccountService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

	@Autowired
	private AccountEndpoint accountEndpoint;
	
	@Autowired
	private AccountController accountController;

	@Autowired
	private AccountService accountService;

	@Test
	public void contexLoadsAccountEndpoint() throws Exception {
		assertThat(accountEndpoint).isNotNull();
	}
	
	@Test
	public void contexLoadsAccountController() throws Exception {
		assertThat(accountController).isNotNull();
	}
	
	@Test
	public void contexLoadsAccountService() throws Exception {
		assertThat(accountService).isNotNull();
	}
	
}
