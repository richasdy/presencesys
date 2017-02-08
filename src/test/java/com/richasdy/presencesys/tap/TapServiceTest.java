package com.richasdy.presencesys.tap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import javax.sound.midi.Soundbank;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.AssertTrue;

import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.richasdy.presencesys.AbstractTest;
import com.richasdy.presencesys.schedule.Schedule;
import com.richasdy.presencesys.tap.Tap;
import com.richasdy.presencesys.tap.TapRepository;
import com.richasdy.presencesys.tap.TapService;

@Transactional
public class TapServiceTest extends AbstractTest {

	// this is integration test
	// service dont have validation, controller have
	// independent test with initialization db dml
	// no change db status, no left some garbage data in db

	@Autowired
	private TapService service;
	private Tap foo;

	@Before
	public void init() {
		foo = new Tap();
		foo.setIdKelompok(99);
		foo.setIdUser(99);
		foo.setIdSchedule(99);
		foo.setIdCard(99);
		foo.setIdMachine(99);
		foo.setKelompokNama("fooKelompokNama");
		foo.setUserNumber("fooUserNumber");
		foo.setUserNama("fooUserNama");
		foo.setScheduleTipe("fooScheduleTipe");
		foo.setScheduleNote("fooScheduleNote");
		foo.setScheduleTanggal(new Date());
		foo.setScheduleStart(new Date());
		foo.setScheduleStop(new Date());
		foo.setCardNumber("fooCardNumber");
		foo.setMachineIp("fooMachineIp");
		foo.setStatus("fooStatus");
		foo.setNote("fooNote");

		foo = service.save(foo);
	}

	@After
	public void destroy() {
		// TapService.delete(foo.getId());
	}

	@Test
	public void save() {

		// error jika di test semua, jika di test sendiri all pass

		// prepare
		Tap bar = new Tap();
		bar.setIdKelompok(100);
		bar.setIdUser(100);
		bar.setIdSchedule(100);
		bar.setIdCard(100);
		bar.setIdMachine(100);
		bar.setKelompokNama("barKelompokNama");
		bar.setUserNumber("barUserNumber");
		bar.setUserNama("barUserNama");
		bar.setScheduleTipe("barScheduleTipe");
		bar.setScheduleNote("barScheduleNote");
		bar.setScheduleTanggal(new Date());
		bar.setScheduleStart(new Date());
		bar.setScheduleStop(new Date());
		bar.setCardNumber("barCardNumber");
		bar.setMachineIp("barMachineIp");
		bar.setStatus("barStatus");
		bar.setNote("barNote");

		long countBefore = service.count();

		// action
		Tap confirm = service.save(bar);

		long countAfter = service.count();

		// delete data
		// handle by transactional
		// TapService.delete(bar.getId());

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected right count", countAfter, countBefore + 1);
		assertEquals("failure - expected same value", bar.getIdSchedule(), confirm.getIdSchedule());

	}

	@Test(expected = ConstraintViolationException.class)
	public void saveValidationErrorEmptyField() {

		// prepare
		Tap bar = new Tap();
		bar.setIdKelompok(100);
		bar.setIdUser(100);
		bar.setIdSchedule(100);
		bar.setIdCard(100);
		bar.setIdMachine(100);
		bar.setKelompokNama("barKelompokNama");
		bar.setUserNumber("barUserNumber");
		bar.setUserNama("barUserNama");
		// validation using hibernate validator
		// bar.setScheduleTipe("barScheduleTipe");
		bar.setScheduleNote("barScheduleNote");
		bar.setScheduleTanggal(new Date());
		bar.setScheduleStart(new Date());
		bar.setScheduleStop(new Date());
		bar.setCardNumber("barCardNumber");
		bar.setMachineIp("barMachineIp");
		bar.setStatus("barStatus");
		bar.setNote("barNote");

		// action
		Tap confirm = service.save(bar);

		// check

	}

	// @Test(expected = DataIntegrityViolationException.class)
	public void saveValidationErrorDupicate() {

		// prepare
		Tap bar = new Tap();
		bar.setIdKelompok(99);
		bar.setIdUser(99);
		bar.setIdSchedule(99);
		bar.setIdCard(99);
		bar.setIdMachine(99);
		bar.setKelompokNama("fooKelompokNama");
		bar.setUserNumber("fooUserNumber");
		bar.setUserNama("fooUserNama");
		bar.setScheduleTipe("fooScheduleTipe");
		bar.setScheduleNote("fooScheduleNote");
		bar.setScheduleTanggal(new Date());
		bar.setScheduleStart(new Date());
		bar.setScheduleStop(new Date());
		bar.setCardNumber("fooCardNumber");
		bar.setMachineIp("fooMachineIp");
		bar.setStatus("fooStatus");

		// action
		Tap confirm = service.save(bar);

		// check

	}

	@Test
	public void update() {

		// prepare
		foo.setIdSchedule(100);

		// action
		Tap confirm = service.update(foo);

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected same value", foo.getIdSchedule(), confirm.getIdSchedule());
		assertThat("failure - expected has idCard updated", confirm,
				hasProperty("idSchedule", is(foo.getIdSchedule())));

	}

	@Test(expected = NullPointerException.class)
	public void updateNotFound() {

		// prepare
		Tap notFound = service.findOne(Integer.MAX_VALUE);

		// action
		Tap confirm = service.update(notFound);

		// check

	}

	@Test(expected = ConstraintViolationException.class)
	public void updateValidationErrorEmptyField() {

		// prepare
		Tap bar = new Tap();
		bar.setIdKelompok(100);
		bar.setIdUser(100);
		bar.setIdSchedule(100);
		bar.setIdCard(100);
		bar.setIdMachine(100);
		bar.setKelompokNama("barKelompokNama");
		bar.setUserNumber("barUserNumber");
		bar.setUserNama("barUserNama");
		// validation using hibernate validator
		// bar.setScheduleTipe("barScheduleTipe");
		bar.setScheduleNote("barScheduleNote");
		bar.setScheduleTanggal(new Date());
		bar.setScheduleStart(new Date());
		bar.setScheduleStop(new Date());
		bar.setCardNumber("barCardNumber");
		bar.setMachineIp("barMachineIp");
		bar.setStatus("barStatus");
		bar.setNote("barNote");
		
		// createdAt tidak boleh null, diset di fungsi save
		bar.setCreatedAt(new Date());

		// action
		// kalau lolos menjadi save
		Tap confirm = service.update(bar);

		// check

	}

	@Test
	public void findOne() {

		// prepare

		// action
		Tap confirm = service.findOne(foo.getId());
		// System.out.println(confirm.toString());

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected same id", foo.getId(), confirm.getId());
	}

	@Test
	public void findOneNotFound() {

		// prepare
		long id = Long.MAX_VALUE;

		// action
		Tap confirm = service.findOne(id);

		// check
		assertTrue("failure - expected null", confirm == null);
	}

	@Test
	public void findAll() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.findAll(page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void count() {

		// prepare

		// action
		long count = service.count();

		// check
		assertTrue("failure - expected count > 0", count > 0);
	}

	@Test
	public void delete() {

		// prepare

		// action
		service.delete(foo.getId());

		Tap confirm = service.findOne(foo.getId());

		// check
		assertTrue("failure - expected null", confirm == null);

	}

	@Test
	public void deleteSoft() {

		// prepare

		// action
		Tap confirm = service.deleteSoft(foo.getId());

		// check
		assertTrue("failure - expected not null", confirm.getDeletedAt() != null);

	}

	@Test
	public void searchByEmptyString() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchEmptyString : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchById() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("id:1", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByIdNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		String searchTerm = "id:" + Long.MAX_VALUE;

		// System.out.println(searchTerm);

		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NumberFormatException.class)
	public void searchByIdWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("id:a", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByIdEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("id:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test
	public void searchByIdKelompok() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		String searchTerm = "idkelompok:" + foo.getIdKelompok();

		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByIdKelompokNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		String searchTerm = "idkelompok:" + Long.MAX_VALUE;

		// System.out.println(searchTerm);

		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NumberFormatException.class)
	public void searchByIdKelompokWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("idkelompok:a", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByIdKelompokEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("idkelompok:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test
	public void searchByIdUser() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		String searchTerm = "iduser:" + foo.getIdUser();

		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByIdUserNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		String searchTerm = "iduser:" + Long.MAX_VALUE;

		// System.out.println(searchTerm);

		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NumberFormatException.class)
	public void searchByIdUserWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("iduser:a", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByIdUserEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("iduser:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	

	@Test
	public void searchByIdSchedule() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		String searchTerm = "idschedule:" + foo.getIdSchedule();

		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByIdScheduleNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		String searchTerm = "idschedule:" + Long.MAX_VALUE;

		// System.out.println(searchTerm);

		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NumberFormatException.class)
	public void searchByIdScheduleWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("idschedule:a", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByIdScheduleEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("idschedule:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchByKelompokNama() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("kelompoknama:fooKelompokNama", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByKelompokNamaNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("kelompoknama:notFoundKelompokNama", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByKelompokNamaEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("kelompoknama:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test
	public void searchByUserNumber() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("usernumber:fooUserNumber", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByUserNumberNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("usernumber:notFoundUserNumber", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByUserNumberEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("usernumber:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test
	public void searchByUserNama() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("usernama:fooUserNama", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByUserNamaNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("usernama:notFoundUserNama", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByUserNamaEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("usernama:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test
	public void searchByScheduleTipe() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("scheduletipe:fooScheduleTipe", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByScheduleTipeNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("scheduletipe:notFoundScheduleTipe", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByScheduleTipeEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("scheduletipe:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test
	public void searchByScheduleNote() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("schedulenote:note", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByScheduleNoteNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("schedulenote:notFoundNote", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByScheduleNoteEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("schedulenote:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	

	@Test
	public void searchByScheduleTanggal() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "scheduletanggal:" + (date.getYear() + 1900) + "-" + String.format("%02d", date.getMonth() + 1)
				+ "-" + date.getDate();

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt"+pageableConfirm);
		// System.out.println("@searchByCreatedAt"+listConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByScheduleTanggalNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		// String searchTerm = "createdat:0000-00-00";
		String searchTerm = "scheduletanggal:1990-10-06";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NullPointerException.class)
	public void searchByScheduleTanggalWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "scheduletanggal:0000";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByScheduleTanggalEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "scheduletanggal:";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchByScheduleStart() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "schedulestart:" + (date.getYear() + 1900) + "-" + String.format("%02d", date.getMonth() + 1) + "-"
				+ date.getDate();

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt"+pageableConfirm);
		// System.out.println("@searchByCreatedAt"+listConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByScheduleStartNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		// String searchTerm = "createdat:0000-00-00";
		String searchTerm = "schedulestart:1990-10-06";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NullPointerException.class)
	public void searchByScheduleStartWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "schedulestart:0000";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByScheduleStartEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "schedulestart:";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchBySchuduleStop() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "schedulestop:" + (date.getYear() + 1900) + "-" + String.format("%02d", date.getMonth() + 1) + "-"
				+ date.getDate();

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt"+pageableConfirm);
		// System.out.println("@searchByCreatedAt"+listConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchBySchuduleStopNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		// String searchTerm = "createdat:0000-00-00";
		String searchTerm = "schedulestop:1990-10-06";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NullPointerException.class)
	public void searchByScheduleStopWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "schedulestop:0000";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByScheduleStopEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "schedulestop:";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test
	public void searchByCardNumber() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("cardnumber:fooCardNumber", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByCardNumberNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("cardnumber:notFoundcCardNumber", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByCardNumberEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("cardnumber:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test
	public void searchByMachineIp() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("machineip:fooMachineIp", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByMachineIpNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("machineip:notFoundcMachineIp", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByMachineIpEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("machineip:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchByStatus() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("status:fooStatus", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByStatusNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("status:notFoundStatus", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByStatusEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("status:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByIpNotFound" + pageableConfirm);
		// System.out.println("@searchByIpNotFound" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm == null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchByNote() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("note:note", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByNoteNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("note:notFoundNote", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByNoteEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Tap> pageableConfirm = service.searchBy("note:", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchByCreatedAt() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "createdat:" + (date.getYear() + 1900) + "-" + String.format("%02d", date.getMonth() + 1)
				+ "-" + date.getDate();

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt"+pageableConfirm);
		// System.out.println("@searchByCreatedAt"+listConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByCreatedAtNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		// String searchTerm = "createdat:0000-00-00";
		String searchTerm = "createdat:1990-10-06";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NullPointerException.class)
	public void searchByCreatedAtWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "createdat:0000";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByCreatedAtEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "createdat:";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchByDeteledAt() {

		// prepare
		Tap confirm = service.deleteSoft(foo.getId());
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "deletedat:" + (date.getYear() + 1900) + "-" + String.format("%02d", date.getMonth() + 1)
				+ "-" + date.getDate();

		// System.out.println("@searchByDeletedAt" +
		// service.findOne(foo.getId()));
		// System.out.println("@searchByDeletedAt" + searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByDeletedAt" + pageableConfirm);
		// System.out.println("@searchByDeletedAt" + listConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByDeletedAtNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		// String searchTerm = "createdat:0000-00-00";
		String searchTerm = "deletedat:1990-10-06";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NullPointerException.class)
	public void searchByDeletedAtWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "deletedat:0000";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByDeletedAtEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "deletedat:";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchByUpdatedAt() {

		// prepare
		foo.setKelompokNama("fooNamaUpdate");
		Tap confirm = service.update(foo);
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "updatedat:" + (date.getYear() + 1900) + "-" + String.format("%02d", date.getMonth() + 1)
				+ "-" + date.getDate();

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt"+pageableConfirm);
		// System.out.println("@searchByCreatedAt"+listConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByUpdatedAtNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		// String searchTerm = "createdat:0000-00-00";
		String searchTerm = "updatedat:1990-10-06";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test(expected = NullPointerException.class)
	public void searchByUpdatedAtWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "updatedat:0000";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void searchByUpdatedAtEmpty() {

		// prepare
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "updatedat:";
		// String searchTerm = "createdat:0000-00-00";

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Tap> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

}
