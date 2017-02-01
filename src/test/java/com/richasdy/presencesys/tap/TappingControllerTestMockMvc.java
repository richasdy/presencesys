package com.richasdy.presencesys.tap;

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
import java.util.Date;
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
import com.richasdy.presencesys.card.Card;
import com.richasdy.presencesys.card.CardService;
import com.richasdy.presencesys.tap.Tap;
import com.richasdy.presencesys.tap.TapService;
import com.richasdy.presencesys.user.User;
import com.richasdy.presencesys.user.UserService;
import com.richasdy.presencesys.domain.Quote;
import com.richasdy.presencesys.kelompok.Kelompok;
import com.richasdy.presencesys.kelompok.KelompokService;
import com.richasdy.presencesys.machine.Machine;
import com.richasdy.presencesys.machine.MachineService;
import com.richasdy.presencesys.schedule.Schedule;
import com.richasdy.presencesys.schedule.ScheduleService;

@Transactional
public class TappingControllerTestMockMvc extends AbstractControllerTest {

	// connection using mockmvc
	// this is integration test

	@Autowired
	private MachineService machineService;

	@Autowired
	private CardService cardService;

	@Autowired
	private UserService userService;

	@Autowired
	private KelompokService kelompokService;

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private TapService tapService;

	private Machine fooMachine;
	private Card fooCard;
	private User fooUser;
	private Kelompok fooKelompok;
	private Schedule fooSchedule;
	private Tap foo;

	@Before
	public void setUp() {
		super.setUp();

		foo = new Tap();
		foo.setIdSchedule(99);
		foo.setIdUser(99);
		foo.setIdKelompok(99);
		foo.setScheduleTanggal(new Date());
		foo.setScheduleStart(new Date());
		foo.setScheduleStop(new Date());
		foo.setUserNumber("fooUserNumber");
		foo.setScheduleTipe("fooScheduleTipe");
		foo.setNama("fooNama");
		foo.setStatus("fooStatus");
		foo.setNote("fooNote");
		foo = tapService.save(foo);

		// TAPPING TESTING

		fooMachine = new Machine();
		fooMachine.setIp("127.0.0.1");
		fooMachine = machineService.save(fooMachine);

		fooCard = new Card();
		fooCard.setCardNumber("fooCardNumber");
		fooCard.setActivated(true);
		fooCard.setActivatedAt(new Date());
		fooCard = cardService.save(fooCard);

		fooKelompok = new Kelompok();
		fooKelompok.setNama("fooNama");
		fooKelompok.setNote("fooNote");
		fooKelompok = kelompokService.save(fooKelompok);

		fooUser = new User();
		fooUser.setIdCard(fooCard.getId());
		fooUser.setIdKelompok(fooKelompok.getId());
		fooUser.setUserNumber("fooUserNumber");
		fooUser.setNama("fooNama");
		fooUser.setNote("fooNote");
		fooUser = userService.save(fooUser);

		fooSchedule = new Schedule();
		fooSchedule.setIdKelompok(fooUser.getIdKelompok());
		fooSchedule.setTipe("fooTipe");
		fooSchedule.setNote("fooNote");
		fooSchedule.setTanggal(new Date());
		fooSchedule.setStart(new Date(System.currentTimeMillis() - 3600 * 1000));
		fooSchedule.setStop(new Date(System.currentTimeMillis() + 3600 * 1000));
		fooSchedule = scheduleService.save(fooSchedule);

	}

	@Test
	public void indexNull() throws Exception {

		// prepare
		String uri = "/tapping";

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.ALL))
				// .andDo(print())
				.andReturn();

		// System.out.println("@index : " + result);

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// System.out.println("@index : " + content);

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected error message : null", content, "error : cardNumber tidak boleh kosong");

	}

	@Test
	public void indexMachineNotRegistered() throws Exception {

		// prepare
		String uri = "/tapping";

		// remove fooMachine
		machineService.delete(fooMachine.getId());

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.ALL))
				// .andDo(print())
				.andReturn();

		// System.out.println("@index : " + result);

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// System.out.println("@index : " + content);

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected error message : unregistered machine", content,
				"error : perangkat tidak terdaftar");

	}

	@Test
	public void indexCardNotRegistered() throws Exception {

		// prepare
		String uri = "/tapping";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("cardNumber", fooCard.getCardNumber());

		// remove card to make unregistered
		cardService.delete(fooCard.getId());

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).params(params).accept(MediaType.ALL))
				// .andDo(print())
				.andReturn();

		// System.out.println("@index : " + result);

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// System.out.println("@index : " + content);

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected null error message", content, "error : kartu tidak terdaftar");

	}

	@Test
	public void indexCardNotActived() throws Exception {

		// prepare
		String uri = "/tapping";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("cardNumber", fooCard.getCardNumber());

		// deactive card
		fooCard.setActivated(false);
		cardService.update(fooCard);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).params(params).accept(MediaType.ALL))
				// .andDo(print())
				.andReturn();

		// System.out.println("@index : " + result);

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// System.out.println("@index : " + content);

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected null error message", content, "error : kartu belum aktif");

	}

	@Test
	public void indexCardNotAssociated() throws Exception {

		// prepare
		String uri = "/tapping";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("cardNumber", fooCard.getCardNumber());

		// hapus fooUser supaya kartu tidak terasosiasi
		// System.out.println(fooUser.toString());
		userService.delete(fooUser.getId());

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).params(params).accept(MediaType.ALL))
				// .andDo(print())
				.andReturn();

		// System.out.println("@index : " + result);

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// System.out.println("@index : " + content);

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected null error message", content, "error : kartu belum terasosiasi dengan user");

	}

	@Test
	public void indexUserNotAssociatedWithKelompok() throws Exception {

		// prepare
		String uri = "/tapping";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("cardNumber", fooCard.getCardNumber());

		// hapus idKelompok fooUser supaya user tidak terasosiasi dengan
		// kelompok
		// System.out.println(fooUser.toString());
		fooUser.setIdKelompok(0);
		userService.update(fooUser);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).params(params).accept(MediaType.ALL))
				// .andDo(print())
				.andReturn();

		// System.out.println("@index : " + result);

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// System.out.println("@index : " + content);

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected null error message", content,
				"error : user belum terasosiasi dengan kelompok");

	}

	@Test
	public void indexScheduleNotExist() throws Exception {

		// prepare
		String uri = "/tapping";

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("cardNumber", fooCard.getCardNumber());

		// hapus idKelompok fooUser supaya user tidak terasosiasi dengan
		// kelompok
		// System.out.println(fooUser.toString());
		fooSchedule.setStart(new Date(System.currentTimeMillis() + 3600 * 1000));
		scheduleService.update(fooSchedule);

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).params(params).accept(MediaType.ALL))
				// .andDo(print())
				.andReturn();

		// System.out.println("@index : " + result);

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// System.out.println("@index : " + content);

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected null error message", content, "error : tidak ada jadwal tapping sekarang");

	}

	@Test
	public void index() throws Exception {

		// prepare
		String uri = "/tapping";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("cardNumber", fooCard.getCardNumber());

		// action
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(uri).params(params).accept(MediaType.ALL))
				.andDo(print()).andReturn();

		// System.out.println("@index : " + result);

		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();

		// System.out.println("@index : " + content);

		// check
		assertEquals("failure - expected HTTP Status 200", HttpStatus.OK.value(), status);
		assertEquals("failure - expected null error message", content, "success : presensi berhasil");

	}

}
