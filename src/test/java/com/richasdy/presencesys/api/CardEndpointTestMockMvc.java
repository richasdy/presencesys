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
import com.richasdy.presencesys.domain.Card;
import com.richasdy.presencesys.service.CardService;

@Transactional
public class CardEndpointTestMockMvc extends AbstractControllerTest {

	// connection using mockmvc

	@Autowired
	private CardService service;

	private Card foo;

	@Before
	public void setUp() {
		super.setUp();

		foo = new Card();
		foo.setCardNumber("000000000000");

		foo = service.save(foo);

	}

	@Test
	public void index() throws Exception {

		// prepare
		String uri = "/apiv1/card";

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertEquals("failure - expected HTTP Status 200", status, HttpStatus.OK.value());
		assertTrue("failure - expected HTTP response body have value", content.trim().length() > 0);

	}

	@Test
	public void show() throws Exception {

		// prepare
		String uri = "/apiv1/card/{id}";
		int id = foo.getId();

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertTrue("failure - expected HTTP response body to have value", content.trim().length() > 0);

		Card confirm = super.mapFromJson(content, Card.class);
		assertEquals("failure - expected same value ", foo.getCardNumber(), confirm.getCardNumber());

	}

	@Test
	public void showNotFound() throws Exception {

		// prepare
		String uri = "/apiv1/card/{id}";
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
		String uri = "/apiv1/card";

		Card bar = new Card();
		bar.setCardNumber("999999999999");

		String inputJson = super.mapToJson(bar);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertEquals("failure - expected HTTP Status 201", HttpStatus.CREATED.value(), status);
		assertTrue("failure - expected HTTP response body to have value", content.trim().length() > 0);

		Card confirm = super.mapFromJson(content, Card.class);

		assertThat("failure - expected not null", confirm, notNullValue());
		assertThat("failure - expected not null id", confirm.getId(), notNullValue());
		assertEquals("failure - expected same field value", bar.getCardNumber(), confirm.getCardNumber());

	}

	@Test
	public void update() throws Exception {

		// prepare
		String uri = "/apiv1/card/{id}";
		int id = foo.getId();

		Card bar = service.findOne(id);
		bar.setCardNumber("0000000001");

		String inputJson = super.mapToJson(bar);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(uri, id).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertTrue("failure - expected HTTP response body to have value", content.trim().length() > 0);

		Card confirm = super.mapFromJson(content, Card.class);

		assertThat("failure - expected not null", confirm, notNullValue());
		assertEquals("failure - expected account.id unchanged", bar.getId(), confirm.getId());
		assertEquals("failure - expected updated field value", bar.getCardNumber(), confirm.getCardNumber());

	}

	@Test
	public void delete() throws Exception {

		// prepare
		String uri = "/apiv1/card/{id}";
		int id = foo.getId();

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertEquals("failure - expected HTTP Status 401", HttpStatus.GONE.value(), status);
		assertTrue("failure - expected HTTP response body to be empty", content.trim().length() == 0);

		Card confirm = service.findOne(id);

		assertThat("failure - expected null", confirm, nullValue());

	}

}
