package com.richasdy.presencesys.machine;

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
import com.richasdy.presencesys.machine.Machine;
import com.richasdy.presencesys.machine.MachineRepository;
import com.richasdy.presencesys.machine.MachineService;

@Transactional
public class MachineServiceTest extends AbstractTest {

	// this is integration test
	// service dont have validation, controller have
	// independent test with initialization db dml
	// no change db status, no left some garbage data in db

	@Autowired
	private MachineService service;
	private Machine foo;

	@Before
	public void init() {
		foo = new Machine();
		foo.setIp("fooIp");
		foo.setNote("fooNote");

		foo = service.save(foo);
	}

	@After
	public void destroy() {
		// MachineService.delete(foo.getId());
	}

	@Test
	public void save() {

		// error jika di test semua, jika di test sendiri all pass

		// prepare
		Machine bar = new Machine();
		bar.setIp("barIp");
		bar.setNote("barNote");

		long countBefore = service.count();

		// action
		Machine confirm = service.save(bar);

		long countAfter = service.count();

		// delete data
		// handle by transactional
		// MachineService.delete(bar.getId());

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected right count", countAfter, countBefore + 1);
		assertEquals("failure - expected same value", bar.getIp(), confirm.getIp());

	}

	@Test(expected = ConstraintViolationException.class)
	public void saveValidationErrorEmptyField() {

		// prepare
		Machine bar = new Machine();
		// bar.setIp("barIp");
		bar.setNote("barNote");

		// action
		Machine confirm = service.save(bar);

		// check

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void saveValidationErrorDupicate() {

		// prepare
		Machine bar = new Machine();
		bar.setIp("fooIp");
		bar.setNote("fooNote");

		// action
		Machine confirm = service.save(bar);

		// check

	}

	@Test
	public void update() {

		// prepare
		foo.setIp("fooIpUpdate");

		// action
		Machine confirm = service.update(foo);

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected same value", foo.getIp(), confirm.getIp());
		assertThat("failure - expected has ip updated", confirm, hasProperty("ip", is(foo.getIp())));

	}

	@Test(expected = NullPointerException.class)
	public void updateNotFound() {

		// prepare
		Machine notFound = service.findOne(Integer.MAX_VALUE);

		// action
		Machine confirm = service.update(notFound);

		// check

	}

	@Test(expected = ConstraintViolationException.class)
	public void updateValidationErrorEmptyField() {

		// prepare
		Machine bar = new Machine();
		bar.setId(Integer.MAX_VALUE);
		// bar.setIp("barIp");
		bar.setNote("barNote");
		// createdAt tidak boleh null, diset di fungsi save
		bar.setCreatedAt(new Date());

		// action
		// kalau lolos menjadi save
		Machine confirm = service.update(bar);

		// check

	}

	@Test
	public void findOne() {

		// prepare

		// action
		Machine confirm = service.findOne(foo.getId());
		System.out.println(confirm.toString());

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected same id", foo.getId(), confirm.getId());
	}

	@Test
	public void findOneNotFound() {

		// prepare
		int id = Integer.MAX_VALUE;

		// action
		Machine confirm = service.findOne(id);

		// check
		assertTrue("failure - expected null", confirm == null);
	}

	@Test
	public void findAll() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Machine> pageableConfirm = service.findAll(page);
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

		Machine confirm = service.findOne(foo.getId());

		// check
		assertTrue("failure - expected null", confirm == null);

	}

	@Test
	public void deleteSoft() {

		// prepare

		// action
		Machine confirm = service.deleteSoft(foo.getId());

		// check
		assertTrue("failure - expected not null", confirm.getDeletedAt() != null);

	}

	@Test
	public void searchByEmptyString() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Machine> pageableConfirm = service.searchBy("", page);
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
		Page<Machine> pageableConfirm = service.searchBy("id:1", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test(expected = NumberFormatException.class)
	public void searchByIdWrongFormat() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Machine> pageableConfirm = service.searchBy("id:a", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchByIp() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Machine> pageableConfirm = service.searchBy("ip:fooIp", page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// check
		assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchByIpNotFound() {

		// prepare
		Pageable page = new PageRequest(0, 2);

		// action
		Page<Machine> pageableConfirm = service.searchBy("ip:notFoundIp", page);
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
		Page<Machine> pageableConfirm = service.searchBy("note:note", page);
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
		Page<Machine> pageableConfirm = service.searchBy("note:notFoundNote", page);
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
		Page<Machine> pageableConfirm = service.searchBy(searchTerm, page);
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
		Page<Machine> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

//		System.out.println("@searchByCreatedAt" + pageableConfirm);
//		System.out.println("@searchByCreatedAt" + listConfirm);

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
		Page<Machine> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

//		System.out.println("@searchByCreatedAt" + pageableConfirm);
//		System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

	@Test
	public void searchByDeteledAt() {

		// prepare
		Machine confirm = service.deleteSoft(foo.getId());
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "deletedat:" + (date.getYear() + 1900) + "-" + String.format("%02d", date.getMonth() + 1)
				+ "-" + date.getDate();

		// System.out.println("@searchByDeletedAt" +
		// service.findOne(foo.getId()));
		// System.out.println("@searchByDeletedAt" + searchTerm);
		// action
		Page<Machine> pageableConfirm = service.searchBy(searchTerm, page);
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
		Page<Machine> pageableConfirm = service.searchBy(searchTerm, page);
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
		Page<Machine> pageableConfirm = service.searchBy(searchTerm, page);
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
		foo.setIp("fooIpUpdate");
		Machine confirm = service.update(foo);
		Pageable page = new PageRequest(0, 2);
		Date date = new Date();

		// calculation of year and date, by documentation
		String searchTerm = "updatedat:" + (date.getYear() + 1900) + "-" + String.format("%02d", date.getMonth() + 1)
				+ "-" + date.getDate();

		// System.out.println("@searchByCreatedAt"+searchTerm);
		// action
		Page<Machine> pageableConfirm = service.searchBy(searchTerm, page);
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
		Page<Machine> pageableConfirm = service.searchBy(searchTerm, page);
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
		Page<Machine> pageableConfirm = service.searchBy(searchTerm, page);
		List listConfirm = Lists.newArrayList(pageableConfirm);

		// System.out.println("@searchByCreatedAt" + pageableConfirm);
		// System.out.println("@searchByCreatedAt" + listConfirm);

		// check
		// assertTrue("failure - expected not null", pageableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() == 0);

	}

}
