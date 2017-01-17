package com.richasdy.presencesys.service;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.richasdy.presencesys.AbstractTest;
import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.repository.AccountRepository;
import com.richasdy.presencesys.service.AccountService;

@Transactional
public class AccountServiceTest extends AbstractTest {

	// this is integration test
	// service dont have validation, controller have
	// independent test with initialization db dml
	// no change db status, no left some garbage data in db

	@Autowired
	private AccountService service;
	private Account foo;

	@Before
	public void init() {
		foo = new Account();
		foo.setEmail("foo@email.com");
		foo.setPhone("000000000000");
		foo.setUsername("fooUsername");
		foo.setPassword("fooPassword");

		foo = service.save(foo);
	}

	@After
	public void destroy() {
		// accountService.delete(foo.getId());
	}

	@Test
	public void save() {

		// error jika di test semua, jika di test sendiri all pass

		// prepare
		Account bar = new Account();
		bar.setEmail("bar@email.com");
		bar.setPhone("999999999999");
		bar.setUsername("barUsername");
		bar.setPassword("barPassword");

		long countBefore = service.count();

		// action
		Account confirm = service.save(bar);

		long countAfter = service.count();

		// delete data
		// handle by transactional
		// accountService.delete(bar.getId());

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected right count", countAfter, countBefore + 1);
		assertEquals("failure - expected same value", bar.getEmail(), confirm.getEmail());

	}

	@Test(expected = ConstraintViolationException.class)
	public void saveValidationErrorEmptyField() {

		// prepare
		Account bar = new Account();
		bar.setEmail("bar@email.com");
		bar.setPhone("999999999999");
		bar.setUsername("barUsername");

		// action
		Account confirm = service.save(bar);

		// check

	}

	@Test(expected = DataIntegrityViolationException.class)
	public void saveValidationErrorDupicate() {

		// prepare
		Account bar = new Account();
		bar.setEmail("foo@email.com");
		bar.setPhone("000000000000");
		bar.setUsername("fooUsername");
		bar.setPassword("fooPassword");

		// action
		Account confirm = service.save(bar);

		// check

	}

	@Test
	public void update() {

		// prepare
		foo.setEmail("fooUpdate@email.com");

		// action
		Account confirm = service.update(foo);

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected same value", foo.getEmail(), confirm.getEmail());
		assertThat("failure - expected has email updated", confirm, hasProperty("email", is(foo.getEmail())));

	}

	@Test(expected = NullPointerException.class)
	public void updateNotFound() {

		// prepare
		Account notFound = service.findOne(Integer.MAX_VALUE);

		// action
		Account confirm = service.update(notFound);

		// check

	}

	@Test(expected = ConstraintViolationException.class)
	public void updateValidationErrorEmptyField() {

		// prepare
		Account bar = new Account();
		bar.setId(Integer.MAX_VALUE);
		bar.setEmail("bar@email.com");
		bar.setPhone("999999999999");
		// bar.setUsername("barUsername");
		bar.setPassword("barPassword");
		// createdAt tidak boleh null, diset di fungsi save
		bar.setCreatedAt(new Date());

		// action
		// kalau lolos menjadi save
		Account confirm = service.update(bar);

		// check

	}

	@Test
	public void findOne() {

		// prepare

		// action
		Account confirm = service.findOne(foo.getId());

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected same id", foo.getId(), confirm.getId());
	}

	@Test
	public void findOneNotFound() {

		// prepare
		int id = Integer.MAX_VALUE;

		// action
		Account confirm = service.findOne(id);

		// check
		assertTrue("failure - expected null", confirm == null);
	}

	@Test
	public void findAll() {

		// prepare

		// action
		Iterable<Account> iterableConfirm = service.findAll();
		List listConfirm = Lists.newArrayList(iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
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

		Account confirm = service.findOne(foo.getId());

		// check
		assertTrue("failure - expected null", confirm == null);

	}

	@Test
	public void deleteSoft() {

		// prepare

		// action
		Account confirm = service.deleteSoft(foo.getId());

		// check
		assertTrue("failure - expected not null", confirm.getDeletedAt() != null);

	}

	@Test
	public void searchEmptyString() {
		// prepare

		// action
		Iterable<Account> iterableConfirm = service.search("");
		List listConfirm = Lists.newArrayList(iterableConfirm);

		// System.out.println("@searchEmptyString : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchBoolean() {
		// prepare

		// action
		Iterable<Account> iterableConfirm = service.search("false");
		List listConfirm = Lists.newArrayList(iterableConfirm);

		// System.out.println("@searchBoolean : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchDate() {
		// prepare

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String nowString = dateFormat.format(now);

		// System.out.println("@searchDateTest : " + nowString);

		// action
		Iterable<Account> iterableConfirm = service.search(nowString);
		List listConfirm = Lists.newArrayList(iterableConfirm);

		// System.out.println("@searchDateTest : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchString() {

		// prepare

		// action
		Iterable<Account> iterableConfirm = service.search(foo.getEmail());
		List listConfirm = Lists.newArrayList(iterableConfirm);

		// System.out.println("@searchString : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void searchBy() {

		// prepare

		// action
		// Iterable<Account> iterableConfirm = service.searchBy(foo.getEmail());
//		Iterable<Account> iterableConfirm = service.searchBy("id:1");
//		Iterable<Account> iterableConfirm = service.searchBy("email:admin1@email.com");
		Iterable<Account> iterableConfirm = service.searchBy("email:admin1@");
//		Iterable<Account> iterableConfirm = service.searchBy("phone:999999991");
//		Iterable<Account> iterableConfirm = service.searchBy("username:admin1");
//		Iterable<Account> iterableConfirm = service.searchBy("note:sample note admin1");
//		Iterable<Account> iterableConfirm = service.searchBy("permissions:admin");
//		Iterable<Account> iterableConfirm = service.searchBy("activated:true");
//		Iterable<Account> iterableConfirm = service.searchBy("activatedat:2017-01-17");
//		Iterable<Account> iterableConfirm = service.searchBy("lastlogin:2017-01-17");
//		Iterable<Account> iterableConfirm = service.searchBy("createdat:2017-01-17");
//		Iterable<Account> iterableConfirm = service.searchBy("updatedat:2017-01-17");
//		Iterable<Account> iterableConfirm = service.searchBy("deletedat:2017-01-17");

		

		List listConfirm = Lists.newArrayList(iterableConfirm);

		System.out.println("@searchBy : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

}
