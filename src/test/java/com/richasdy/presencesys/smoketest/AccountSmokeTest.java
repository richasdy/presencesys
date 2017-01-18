package com.richasdy.presencesys.smoketest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.richasdy.presencesys.api.AccountEndpoint;
import com.richasdy.presencesys.controller.AccountController;
import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.repository.AccountRepository;
import com.richasdy.presencesys.service.AccountService;
import com.richasdy.presencesys.service.AccountServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountSmokeTest {

	// @Autowired
	// private Account account;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountEndpoint accountEndpoint;

	@Autowired
	private AccountController accountController;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountServiceImpl accountServiceImpl;

	// @Test
	// public void contexLoadsAccount() throws Exception {
	// assertThat(account).isNotNull();
	// }

	@Test
	public void contexLoadsAccountRepository() throws Exception {
		assertThat(accountRepository).isNotNull();
	}

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
	
	@Test
	public void contexLoadsAccountServiceImpl() throws Exception {
		assertThat(accountServiceImpl).isNotNull();
	}

}
