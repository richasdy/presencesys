package com.richasdy.presencesys.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.RestTemplate;

//import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richasdy.presencesys.PresencesysApplication;
import com.richasdy.presencesys.api.AccountEndpoint;
import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.domain.Quote;
import com.richasdy.presencesys.domain.Value;
import com.richasdy.presencesys.service.AccountService;

import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;

@RunWith(SpringRunner.class)

// @SpringBootTest(classes = PresencesysApplication.class, webEnvironment =
// SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // autoconfigure
																			// PresencesysApplication.class
// @Transactional // entah kenapa transactional tak berguna
public class AccountEndpointTestRestTemplate {

	// use resttemplate for clientapp
	// because you need to consume api

	// kyknya g bisa pakai transactional
	// soalnya entitas luar dari api server

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	private String baseUri;

	private Account foo;

	@Before
	public void setUp() {

		baseUri = "http://localhost:" + port + "/apiv1/account";

		foo = new Account();
		foo.setEmail("foo@email.com");
		foo.setPhone("000000000000");
		foo.setUsername("fooUsername");
		foo.setPassword("fooPassword");

		// System.out.println("@setup "+foo.toString());

		String uri = baseUri;

		// System.out.println(uri);

		HttpEntity<Account> request = new HttpEntity<Account>(foo);
		ResponseEntity<Account> response = testRestTemplate.postForEntity(uri, request, Account.class);

		// System.out.println("@setup" + response.getBody().toString());

		foo = response.getBody();

		// System.out.println("@setup" + foo.toString());

		// cari info MockRestServiceServer
		// MockRestServiceServer server =
		// MockRestServiceServer.bindTo(restTemplate).build();
	}

	@After
	public void tearDown() {

		String uri = baseUri + "/" + foo.getId();
		testRestTemplate.delete(uri);

	}

	@Test
	public void index() {

		String uri = baseUri;

		ResponseEntity<Account[]> response = testRestTemplate.getForEntity(uri, Account[].class);

		// System.out.println("@index : " + response.toString());
		// System.out.println("@index : " + response.getBody().length);

		assertThat("failure - expect status 200 OK", response.getStatusCode(), is(HttpStatus.OK));
		assertTrue("failure - expect not empty list", response.getBody().length > 0);

	}

	@Test
	public void show() {

		String uri = baseUri + "/" + foo.getId();

		ResponseEntity<Account> response = testRestTemplate.getForEntity(uri, Account.class);

		// System.out.println("@show : " + response.toString());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), notNullValue());

	}

	@Test
	public void save() {

		// prepare
		String uri = baseUri;

		Account bar = new Account();
		bar.setEmail("bar@email.com");
		bar.setPhone("999999999999");
		bar.setUsername("barUsername");
		bar.setPassword("barPassword");

		// action
		HttpEntity<Account> request = new HttpEntity<Account>(bar);
		ResponseEntity<Account> response = testRestTemplate.postForEntity(uri, request, Account.class);

		// System.out.println("@save : " + response.getBody());

		// tear down
		// mebersikan database
		String uriTearDown = baseUri + "/" + bar.getId();
		testRestTemplate.delete(uriTearDown);

		// check
		assertThat("failure - expected status 201 created", response.getStatusCode(), is(HttpStatus.CREATED));

	}

	@Test
	public void update() {

		// prepare
		String uri = baseUri + "/" + foo.getId();

		// System.out.println("@update "+uri);
		// System.out.println("@update "+foo.toString());

		foo.setUsername(foo.getUsername() + "update");

		// System.out.println("@update "+foo.toString());

		HttpEntity<Account> request = new HttpEntity<Account>(foo);
		ResponseEntity<Account> response = testRestTemplate.exchange(uri, HttpMethod.PUT, request, Account.class);

		// System.out.println("@update : " + response);

		assertEquals("failure - ecpected username changed", response.getBody().getUsername(), foo.getUsername());
		assertThat("failure - expected status 200 ok", response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void delete() {

		// prepare
		// create new object in db
		String uriCreated = baseUri;

		Account bar = new Account();
		bar.setEmail("bar@email.com");
		bar.setPhone("999999999999");
		bar.setUsername("barUsername");
		bar.setPassword("barPassword");

		HttpEntity<Account> requestCreated = new HttpEntity<Account>(bar);
		ResponseEntity<Account> responseCreated = testRestTemplate.postForEntity(uriCreated, requestCreated,
				Account.class);

		bar = responseCreated.getBody();

		// action
		String uri = baseUri + "/" + bar.getId();

		ResponseEntity<String> response = testRestTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);

		// System.out.println("@delete : " + response.getBody());

		// check
		assertThat(response.getStatusCode(), is(HttpStatus.GONE));

	}

}
