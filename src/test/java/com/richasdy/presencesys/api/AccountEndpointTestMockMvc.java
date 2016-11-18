package com.richasdy.presencesys.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.richasdy.presencesys.AbstractControllerTest;
import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.service.AccountService;

@Transactional
public class AccountEndpointTestMockMvc extends AbstractControllerTest {

	// connection using mockmvc

	@Autowired
	private AccountService service;

	private Account foo;

	@Before
	public void setUp() {
		super.setUp();

		foo = new Account();
		foo.setEmail("foo@email.com");
		foo.setPhone("000000000000");
		foo.setUsername("fooUsername");
		foo.setPassword("fooPassword");

		foo = service.save(foo);

	}

	@Test
	public void index() throws Exception {

		// prepare
		String uri = "/apiv1/account";

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				// .andDo(print())
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// System.out.println(content);
		// System.out.println(content.length());
		// System.out.println(content.trim());
		// System.out.println(content.trim().length());
		// System.out.println(result.getResponse().getLocale());

		// check
		assertEquals("failure - expected HTTP Status 200", status, HttpStatus.OK.value());
		assertTrue("failure - expected HTTP response body have value", content.trim().length() > 0);

	}

	@Test
	public void show() throws Exception {

		// prepare
		String uri = "/apiv1/account/{id}";
		int id = foo.getId();

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				// .andDo(print())
				// .andExpect(jsonPath("$.id").value(foo.getId()))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertTrue("failure - expected HTTP response body to have value", content.trim().length() > 0);

		Account confirm = super.mapFromJson(content, Account.class);
		assertEquals("failure - expected same email ", foo.getEmail(), confirm.getEmail());
		
	}

	@Test
	public void showNotFound() throws Exception {

		// prepare
		String uri = "/apiv1/account/{id}";
		int id = Integer.MAX_VALUE;

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertEquals("failure - expected HTTP Status 404", HttpStatus.NOT_FOUND.value(), status);
		assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

	}

	@Test
	public void save() throws Exception {

		// prepare
		String uri = "/apiv1/account";

		Account bar = new Account();
		bar.setEmail("bar@email.com");
		bar.setPhone("999999999999");
		bar.setUsername("barUsername");
		bar.setPassword("barPassword");

		String inputJson = super.mapToJson(bar);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

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
		int id = foo.getId();

		Account foo = service.findOne(id);
		foo.setEmail("adminUpdate@email.com");

		String inputJson = super.mapToJson(foo);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertTrue("failure - expected HTTP response body to have value", content.trim().length() > 0);

		Account confirm = super.mapFromJson(content, Account.class);

		assertThat("failure - expected not null", confirm, notNullValue());
		assertEquals("failure - expected account.id unchanged", foo.getId(), confirm.getId());
		assertEquals("failure - expected updated account email", foo.getEmail(), confirm.getEmail());

	}

	@Test
	public void delete() throws Exception {

		// prepare
		String uri = "/apiv1/account/{id}";
		int id = foo.getId();

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertEquals("failure - expected HTTP Status 401", HttpStatus.GONE.value(), status);
		assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

		Account confirm = service.findOne(id);

		assertThat("failure - expected null", confirm, nullValue());

	}

}
