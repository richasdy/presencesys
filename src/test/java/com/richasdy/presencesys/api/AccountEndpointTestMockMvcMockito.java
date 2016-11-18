package com.richasdy.presencesys.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.richasdy.presencesys.AbstractControllerTest;
import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.service.AccountService;

@Transactional
public class AccountEndpointTestMockMvcMockito extends AbstractControllerTest {

	// connection using mockmvc
	// mock stub

	@Mock
	private AccountService service;

	@InjectMocks
	private AccountEndpoint endpoint;

	// private Account foo;

	@Before
	public void setUp() {

		mockMvc = MockMvcBuilders.standaloneSetup(endpoint).build();
		MockitoAnnotations.initMocks(this);

		// foo = new Account();
		// foo.setEmail("foo@email.com");
		// foo.setPhone("000000000000");
		// foo.setUsername("fooUsername");
		// foo.setPassword("fooPassword");

		// foo = service.save(foo);

	}

	private Collection<Account> getEntityListStubData() {
		Collection<Account> list = new ArrayList<Account>();
		list.add(getEntityStubData());
		return list;
	}

	private Account getEntityStubData() {
		Account bar = new Account();
		bar.setId(1);
		bar.setEmail("bar@email.com");
		bar.setPhone("000000000000");
		bar.setUsername("barUsername");
		bar.setPassword("barPassword");

		return bar;
	}

	@Test
	public void index() throws Exception {

		// prepare
		String uri = "/apiv1/account";

		Collection<Account> list = getEntityListStubData();

		when(service.findAll()).thenReturn(list);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// verify that service.findAll() method was invoked once
		verify(service, times(1)).findAll();

		// check
		assertEquals("failure - expected HTTP Status 200", status, HttpStatus.OK.value());
		assertTrue("failure - expected HTTP response body to have value", content.trim().length() > 0);

	}

	@Test
	public void show() throws Exception {

		// prepare
		String uri = "/apiv1/account/{id}";
		Account bar = getEntityStubData();
		int id = bar.getId();

		when(service.findOne(id)).thenReturn(bar);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// verify that service.findOne(id) method was invoked once
		verify(service, times(1)).findOne(id);

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertTrue("failure - expected HTTP response body to have value", content.trim().length() > 0);

	}

	@Test
	public void showNotFound() throws Exception {

		// prepare
		String uri = "/apiv1/account/{id}";
		int id = Integer.MAX_VALUE;

		when(service.findOne(id)).thenReturn(null);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// verify that service.findOne(id) method was invoked once
		verify(service, times(1)).findOne(id);

		// check
		assertEquals("failure - expected HTTP Status 404", HttpStatus.NOT_FOUND.value(), status);
		assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

	}

	@Test
	public void save() throws Exception {

		// prepare
		String uri = "/apiv1/account";

		Account bar = getEntityStubData();
		int id = bar.getId();

		when(service.save(any(Account.class))).thenReturn(bar);

		String inputJson = super.mapToJson(bar);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// verify that service.save() method was invoked once
		verify(service, times(1)).save(any(Account.class));

		// check
		assertEquals("failure - expected HTTP Status 201", HttpStatus.CREATED.value(), status);
		assertTrue("failure - expected HTTP response body to have value", content.trim().length() > 0);

		Account confirm = super.mapFromJson(content, Account.class);

		assertThat("failure - expected not null", confirm, notNullValue());
		assertThat("failure - expected not null id", confirm.getId(), notNullValue());
		assertEquals("failure - expected same email", bar.getEmail(), confirm.getEmail());

	}

	@Test
	public void update() throws Exception {

		// prepare
		String uri = "/apiv1/account/{id}";

		Account bar = getEntityStubData();
		int id = bar.getId();

		bar.setEmail("adminUpdate@email.com");

		// findOne() used in update
		when(service.findOne(id)).thenReturn(bar);
		when(service.update(any(Account.class))).thenReturn(bar);

		String inputJson = super.mapToJson(bar);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// verify that service.xx() method was invoked once
		verify(service, times(1)).findOne(id);
		verify(service, times(1)).update(any(Account.class));

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertTrue("failure - expected HTTP response body to have value", content.trim().length() > 0);

		Account confirm = super.mapFromJson(content, Account.class);

		assertThat("failure - expected not null", confirm, notNullValue());
		assertEquals("failure - expected account.id unchanged", bar.getId(), confirm.getId());
		assertEquals("failure - expected updated account email", bar.getEmail(), confirm.getEmail());

	}

	 @Test
	public void delete() throws Exception {

		// prepare
		String uri = "/apiv1/account/{id}";
		
		Account bar = getEntityStubData();
		int id = bar.getId();
		

		// findOne() used in delete
		when(service.findOne(id)).thenReturn(bar);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		// verify that service.xx() method was invoked once
		verify(service, times(1)).findOne(id);
		verify(service, times(1)).delete(id);

		// check
		assertEquals("failure - expected HTTP Status 401", HttpStatus.GONE.value(), status);
		assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

	}

}
