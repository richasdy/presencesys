package com.richasdy.presencesys.kelompok;

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
import com.richasdy.presencesys.kelompok.Kelompok;
import com.richasdy.presencesys.kelompok.KelompokService;
import com.richasdy.presencesys.domain.Quote;

@Transactional
public class KelompokControllerTestMockMvc extends AbstractControllerTest {

	// connection using mockmvc
	// this is integration test

	@Autowired
	private KelompokService service;

	private Kelompok foo;

	@Before
	public void setUp() {
		super.setUp();

		foo = new Kelompok();
		foo.setNama("fooNama");
		foo.setNote("fooNote");

		foo = service.save(foo);

	}

	@Test
	public void index() throws Exception {

		// prepare
		String uri = "/kelompok";

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.ALL))
				// .andDo(print())
				.andReturn();

		Map<String, Object> model = result.getModelAndView().getModel();
		String view = result.getModelAndView().getViewName();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		assertTrue("failure - expected model attribute listKelompok", model.containsKey("listEntity"));
		assertTrue("failure - expected model attribute pageName", model.containsKey("pageName"));
		assertTrue("failure - expected model attribute pageNameDesc", model.containsKey("pageNameDesc"));
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected view kelompok/index", "kelompok/index", view);

	}

	@Test
	public void create() throws Exception {

		// prepare
		String uri = "/kelompok/create";

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.ALL)).andReturn();

		Map<String, Object> model = result.getModelAndView().getModel();
		String view = result.getModelAndView().getViewName();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		// assertTrue("failure - expected model attribute kelompok",
		// model.containsKey("entity"));
		assertTrue("failure - expected model attribute kelompok", model.containsKey("kelompok"));
		// assertTrue("failure - expected model attribute kelompok",
		// model.get("entity") != null);
		assertTrue("failure - expected model attribute kelompok", model.get("kelompok") != null);
		assertTrue("failure - expected model attribute pageName", model.containsKey("pageName"));
		assertTrue("failure - expected model attribute pageNameDesc", model.containsKey("pageNameDesc"));
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected view kelompok/index", "kelompok/create", view);

	}

	@Test
	public void save() throws Exception {

		// prepare
		String uri = "/kelompok";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("nama", "barNama");
		params.set("note", "barNote");

		// action
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.ALL).accept(MediaType.ALL)
						.params(params))
				.andExpect(model().hasNoErrors()).andExpect(status().is3xxRedirection()).andReturn();

		// Map<String, Object> model = result.getModelAndView().getModel();
		// String view = result.getModelAndView().getViewName();
		// String content = result.getResponse().getContentAsString();
		// int status = result.getResponse().getStatus();
		// String error = result.getResponse().getErrorMessage();

		// check
		// assertThat("failure - expected null error", error, nullValue());
		// assertEquals("failure - expected HTTP Status 3XX", 3, status / 100);

		// wrong assert
		// view name = redirect:/kelompok/5
		// assertThat("failure - expected null view", view, nullValue());
		// content = ""
		// assertThat("failure - expected null content", content, nullValue());
		// model = <{}>
		// assertThat("failure - expected null model", model, nullValue());

	}

	@Test
	public void saveValidationErrorEmptyField() throws Exception {

		// prepare
		String uri = "/kelompok";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		// params.set("nama", "barNama");
		params.set("note", "barNote");
		// System.out.println(params);

		// action
		// check using mockmvc assert
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.ALL).accept(MediaType.ALL).params(params))
				.andExpect(model().hasErrors()).andReturn();

		// Map<String, Object> model = result.getModelAndView().getModel();
		// String view = result.getModelAndView().getViewName();
		// String content = result.getResponse().getContentAsString();
		// int status = result.getResponse().getStatus();
		// String error = result.getResponse().getErrorMessage();

		// check
		// wrong assert
		// error = null // --> gak guna
		// assertTrue("failure - expected error", error.length() > 0);

	}

	@Test
	public void show() throws Exception {

		// prepare
		String uri = "/kelompok/{id}";
		long id = foo.getId();

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
		assertEquals("failure - expected view kelompok/show", "kelompok/show", view);

	}

	@Test
	public void showOtherAssertTechniqueShowcase() throws Exception {

		// prepare
		String uri = "/kelompok/{id}";
		long id = foo.getId();

		// action
		mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL))
				.andExpect(model().attributeExists("pageName")).andExpect(model().attributeExists("pageNameDesc"))
				.andExpect(model().attributeExists("entity"))
				.andExpect(model().attribute("entity", hasProperty("nama", is(foo.getNama()))))
				.andExpect(model().attributeExists("entity")).andExpect(view().name("kelompok/show"));

	}

	@Test(expected = NestedServletException.class)
	public void showNotFound() throws Exception {

		// prepare
		String uri = "/kelompok/{id}";
		int id = Integer.MAX_VALUE;

		// action
		// proses mock tidak jalan, null pointer di theamleaf.
		// saat akses kelompok.id padahal kelompok = null
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL))
				.andExpect(model().attributeExists("entity")).andExpect(model().attribute("entity", nullValue()))
				.andReturn();

	}

	@Test
	public void edit() throws Exception {

		// prepare
		String uri = "/kelompok/{id}/edit";
		long id = foo.getId();

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL)).andReturn();

		Map<String, Object> model = result.getModelAndView().getModel();
		String view = result.getModelAndView().getViewName();
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// check
		// assertTrue("failure - expected model attribute kelompok",
		// model.containsKey("entity"));
		assertTrue("failure - expected model attribute kelompok", model.containsKey("kelompok"));
		// assertTrue("failure - expected model attribute kelompok",
		// model.get("entity") != null);
		assertTrue("failure - expected model attribute kelompok", model.get("kelompok") != null);
		assertTrue("failure - expected model attribute pageName", model.containsKey("pageName"));
		assertTrue("failure - expected model attribute pageNameDesc", model.containsKey("pageNameDesc"));
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected view kelompok/edit", "kelompok/edit", view);

	}

	@Test
	public void update() throws Exception {

		// prepare
		String uri = "/kelompok/{id}/update";
		long id = foo.getId();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("nama", "fooNamaUpdate");
		params.set("note", "fooNoteUpdate");

		// action
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post(uri, id).contentType(MediaType.ALL).accept(MediaType.ALL)
						.params(params))
				.andExpect(model().hasNoErrors()).andExpect(status().is3xxRedirection()).andReturn();

		// Map<String, Object> model = result.getModelAndView().getModel();
		// String view = result.getModelAndView().getViewName();
		// String content = result.getResponse().getContentAsString();
		// int status = result.getResponse().getStatus();

		// check
		// assertEquals("failure - expected HTTP Status 3XX", 3, status / 100);

		// error = null // --> gak guna
		// assertThat("failure - expected null error", error, nullValue());

	}

	@Test
	public void updateValidationErrorEmptyField() throws Exception {

		// prepare
		String uri = "/kelompok/{id}/update";
		long id = foo.getId();

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		// params.set("nama", "fooNamaUpdate");
		params.set("note", "fooNoteUpdate");

		// action
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(uri, id).contentType(MediaType.ALL).accept(MediaType.ALL).params(params))
				.andExpect(model().hasErrors()).andReturn();

		// Map<String, Object> model = result.getModelAndView().getModel();
		// String view = result.getModelAndView().getViewName();
		// String content = result.getResponse().getContentAsString();
		// int status = result.getResponse().getStatus();
		// String error = result.getResponse().getErrorMessage();

		// check
		// error = null // --> gak guna
		// assertThat("failure - expected null error", error, nullValue());
		// assertEquals("failure - expected HTTP Status 3XX", 3, status / 100);

	}

	@Test
	public void updateHackingPosibility() throws Exception {

		// change other data
		// accont.id != pathVariable
		// SOLUSI : check controller
		// JIKA sudah disolusikan :
		// hapus model().hasNoErrors()) dibawah, ganti dengan
		// view().name("kelompok/edit")
		// ganti nama test menjadi updateValidationErrorNotConsistentId

		// prepare
		String uri = "/kelompok/{id}/update";
		int id = 1;

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("nama", "fooNamaUpdate");
		params.set("note", "fooNoteUpdate");

		// action
		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post(uri, id).contentType(MediaType.ALL).accept(MediaType.ALL).params(params))
				.andExpect(model().hasNoErrors()).andReturn();

		System.out.println(service.findOne(id).toString());

	}

	@Test
	public void delete() throws Exception {

		// prepare
		String uri = "/kelompok/{id}/delete";
		long id = foo.getId();

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/kelompok")).andReturn();

		// Map<String, Object> model = result.getModelAndView().getModel();
		// String view = result.getModelAndView().getViewName();
		// String content = result.getResponse().getContentAsString();
		// int status = result.getResponse().getStatus();

		// check
		// assertEquals("failure - expected HTTP Status 3XX", 3, status / 100);
	}

	@Test(expected = NestedServletException.class)
	public void deleteNotFound() throws Exception {

		// prepare
		String uri = "/kelompok/{id}/delete";
		int id = Integer.MAX_VALUE;

		// action
		// proses mock tidak jalan, null pointer di controller.
		// saat akses kelompok.activated padahal kelompok = null
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.ALL)).andDo(print())
				.andReturn();

		// Map<String, Object> model = result.getModelAndView().getModel();
		// String view = result.getModelAndView().getViewName();
		// String content = result.getResponse().getContentAsString();
		// int status = result.getResponse().getStatus();

		// check
		// assertEquals("failure - expected HTTP Status 3XX", 3, status / 100);
	}

}
