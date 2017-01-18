package com.richasdy.presencesys.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.RestTemplate;

//import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richasdy.presencesys.account.Account;
import com.richasdy.presencesys.account.AccountEndpoint;
import com.richasdy.presencesys.account.AccountService;
import com.richasdy.presencesys.domain.Quote;
import com.richasdy.presencesys.domain.Value;

import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;

public class BasicUseRestTemplate {

	@Test
	public void allowedOperationTest() {

		String uri = "http://gturnquist-quoters.cfapps.io/api/random";
		RestTemplate restTemplate = new RestTemplate();

		Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow(uri);
		HttpMethod[] supportedMethods = { HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE };

		System.out.println("@allowedOperationTest : " + optionsForAllow.toString());

		assertTrue("failure - expected allowed GET POST PUT DELETE ",
				optionsForAllow.containsAll(Arrays.asList(supportedMethods)));

	}

	@Test
	public void basicUse() throws JsonProcessingException, IOException {

		String uri = "http://gturnquist-quoters.cfapps.io/api/random";
		RestTemplate restTemplate = new RestTemplate();

		// GENERAL FUNCTION
		// dipakai di put dan delete supaya dapat httpentity
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

		System.out.println("@BasicUse : " + response.toString());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));

		// GET JSON BODY CONTENT
		ObjectMapper mapper = new ObjectMapper();

		JsonNode root = mapper.readTree(response.getBody());
		JsonNode name = root.path("type");

		assertThat(name.asText(), is("success"));

	}

	@Test
	public void getForEntityClassArray() {

		String uri = "http://gturnquist-quoters.cfapps.io/api";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Quote[]> response = restTemplate.getForEntity(uri, Quote[].class);

		System.out.println("@getForEntityClassArray : " + response.getBody());

		assertThat("failure - expected status 200", response.getStatusCode(), is(HttpStatus.OK));
		assertThat("failure - expected not null body", response.getBody(), notNullValue());

	}

	@Test
	public void getForEntityString() {

		String uri = "http://gturnquist-quoters.cfapps.io/api/1";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

		System.out.println("@getForEntityString : " + response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody(), notNullValue());

		assertTrue(response.getBody().contains("1"));
		assertTrue(response.getBody()
				.contains("Working with Spring Boot is like pair-programming with the Spring developers."));

	}

	// @Test
	public void postForEntity() {

		// Method not allowed

		String uri = "http://gturnquist-quoters.cfapps.io/api/random";
		RestTemplate restTemplate = new RestTemplate();

		Value value = new Value();
		value.setId(Long.MAX_VALUE);
		value.setQuote("yang pergi biarlah pergi");

		Quote quote = new Quote();
		quote.setType("sucess");
		quote.setValue(value);

		ResponseEntity<Quote> response = restTemplate.postForEntity(uri, quote, Quote.class);

		Quote confirm = response.getBody();

		System.out.println("@postForEntity : " + response.getBody());
		System.out.println("@postForEntity : " + confirm.toString());

		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

	}

	// @Test
	public void exchangePut() {

		// Method not allowed

		// prepare
		String uri = "http://gturnquist-quoters.cfapps.io/api/random";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Quote> response = restTemplate.getForEntity(uri, Quote.class);

		Quote quote = response.getBody();
		quote.setType("Success update");

		// action
		HttpEntity<Quote> request = new HttpEntity<Quote>(quote);
		ResponseEntity<Quote> confirm = restTemplate.exchange(uri, HttpMethod.PUT, request, Quote.class);

		System.out.println("@exchangePut : " + confirm.toString());

		assertThat(confirm.getStatusCode(), is(HttpStatus.OK));
	}

	// @Test
	public void exchangeDelete() {

		// Method not allowed

		String uri = "http://gturnquist-quoters.cfapps.io/api/random";
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.DELETE, null, String.class);

		System.out.println("@exchangeDelete : " + response.getBody());
		assertThat(response.getStatusCode(), is(HttpStatus.GONE));

	}

}
