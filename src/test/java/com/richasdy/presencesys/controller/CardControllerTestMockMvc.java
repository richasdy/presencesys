package com.richasdy.presencesys.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.richasdy.presencesys.AbstractControllerTest;
import com.richasdy.presencesys.account.AccountEndpoint;
import com.richasdy.presencesys.domain.Card;
import com.richasdy.presencesys.domain.Quote;
import com.richasdy.presencesys.service.CardService;

@Transactional
public class CardControllerTestMockMvc extends AbstractControllerTest {

	// connection using mockmvc
	// this is integration test

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
		String uri = "/card";

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.ALL)).andReturn();

		Map<String, Object> model = result.getModelAndView().getModel();
		String view = result.getModelAndView().getViewName();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertTrue("failure - expected model attribute listAccount", model.containsKey("listEntity"));
		assertTrue("failure - expected model attribute pageName", model.containsKey("pageName"));
		assertTrue("failure - expected model attribute pageNameDesc", model.containsKey("pageNameDesc"));
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected view card/index", "card/index", view);

	}

	@Test
	public void create() throws Exception {

		// prepare
		String uri = "/card/create";

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.ALL)).andReturn();

		Map<String, Object> model = result.getModelAndView().getModel();
		String view = result.getModelAndView().getViewName();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertTrue("failure - expected model attribute card", model.containsKey("card"));
		assertTrue("failure - expected model attribute card", model.get("card") != null);
		assertTrue("failure - expected model attribute pageName", model.containsKey("pageName"));
		assertTrue("failure - expected model attribute pageNameDesc", model.containsKey("pageNameDesc"));
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected view card/index", "card/create", view);

	}

	@Test
	public void save() throws Exception {

		// prepare
		String uri = "/card";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("cardNumber", "9999999999");

		// action
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.ALL).accept(MediaType.ALL)
						.params(params))
				.andExpect(model().hasNoErrors()).andExpect(status().is3xxRedirection()).andReturn();

	}

	@Test
	public void saveValidationErrorEmptyField() throws Exception {

		// prepare
		String uri = "/card";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		// params.set("cardNumber", "9999999999");

		// action
		// check using mockmvc assert
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.ALL).accept(MediaType.ALL).params(params))
				.andExpect(model().hasErrors()).andReturn();

	}

	@Test
	public void show() throws Exception {

		// prepare
		String uri = "/card/{id}";
		int id = foo.getId();

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL)).andReturn();

		Map<String, Object> model = result.getModelAndView().getModel();
		String view = result.getModelAndView().getViewName();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertTrue("failure - expected model attribute entity", model.containsKey("entity"));
		assertTrue("failure - expected model attribute entity not null", model.get("entity") != null);
		assertTrue("failure - expected model attribute pageName", model.containsKey("pageName"));
		assertTrue("failure - expected model attribute pageNameDesc", model.containsKey("pageNameDesc"));
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected view card/show", "card/show", view);

	}

	@Test
	public void showOtherAssertTechniqueShowcase() throws Exception {

		// prepare
		String uri = "/card/{id}";
		int id = foo.getId();

		// action
		mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL))
				.andExpect(model().attributeExists("pageName")).andExpect(model().attributeExists("pageNameDesc"))
				.andExpect(model().attributeExists("entity"))
				.andExpect(model().attribute("entity", hasProperty("cardNumber", is(foo.getCardNumber()))))
				.andExpect(model().attributeExists("entity")).andExpect(view().name("card/show"));

	}

	@Test(expected = NestedServletException.class)
	public void showNotFound() throws Exception {

		// prepare
		String uri = "/card/{id}";
		int id = Integer.MAX_VALUE;

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL))
				.andExpect(model().attributeExists("entity")).andExpect(model().attribute("entity", nullValue()))
				.andReturn();

	}

	@Test
	public void edit() throws Exception {

		// prepare
		String uri = "/card/{id}/edit";
		int id = foo.getId();

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL)).andReturn();

		Map<String, Object> model = result.getModelAndView().getModel();
		String view = result.getModelAndView().getViewName();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertTrue("failure - expected model attribute card", model.containsKey("card"));
		assertTrue("failure - expected model attribute card", model.get("card") != null);
		assertTrue("failure - expected model attribute pageName", model.containsKey("pageName"));
		assertTrue("failure - expected model attribute pageNameDesc", model.containsKey("pageNameDesc"));
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected view card/edit", "card/edit", view);

	}

	@Test
	public void update() throws Exception {

		// prepare
		String uri = "/card/{id}/update";
		int id = foo.getId();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("cardNumber", "999999999999");

		// action
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(uri, id).contentType(MediaType.ALL).accept(MediaType.ALL)
						.params(params))
				.andExpect(model().hasNoErrors()).andExpect(status().is3xxRedirection()).andReturn();

	}

	@Test
	public void updateValidationErrorEmptyField() throws Exception {

		// prepare
		String uri = "/card/{id}/update";
		int id = foo.getId();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		// params.set("cardNumber", "999999999999");

		// action
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(uri, id).contentType(MediaType.ALL).accept(MediaType.ALL).params(params))
				.andExpect(model().hasErrors()).andReturn();
	}

	@Test
	public void updateHackingPosibility() throws Exception {

		// prepare
		String uri = "/card/{id}/update";
		int id = 1;

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("cardNumber", "999999999999");

		// action
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(uri, id).contentType(MediaType.ALL).accept(MediaType.ALL).params(params))
				.andExpect(model().hasNoErrors()).andReturn();

		System.out.println(service.findOne(id).toString());

	}

	@Test
	public void delete() throws Exception {

		// prepare
		String uri = "/card/{id}/delete";
		int id = foo.getId();

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/card")).andReturn();

	}

	@Test(expected = NestedServletException.class)
	public void deleteNotFound() throws Exception {

		// prepare
		String uri = "/card/{id}/delete";
		int id = Integer.MAX_VALUE;

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL)).andDo(print())
				.andReturn();

	}

}
