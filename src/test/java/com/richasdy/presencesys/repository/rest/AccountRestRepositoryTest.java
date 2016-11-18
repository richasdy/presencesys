package com.richasdy.presencesys.repository.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.richasdy.presencesys.AbstractTest;
import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.repository.rest.AccountRestRepository;
import com.richasdy.presencesys.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // autoconfigure
public class AccountRestRepositoryTest {

	// this is integration test
	// AccountRestRepository langsung berkomunikasi dengan api
	// repository dont have validation, controller have
	// independent test with initialization db dml
	// no change db status, no left some garbage data in db

	@Autowired
	private AccountRestRepository repository;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private RestTemplate restTemplate;

	private Account foo;

	@Before
	public void init() {

		foo = new Account();
		foo.setEmail("foo@email.com");
		foo.setPhone("000000000000");
		foo.setUsername("fooUsername");
		foo.setPassword("fooPassword");

		foo = repository.save(foo);
	}

	@Test
	public void save() {

		// prepare
		Account bar = new Account();
		bar.setEmail("bar@email.com");
		bar.setPhone("999999999999");
		bar.setUsername("barUsername");
		bar.setPassword("barPassword");

		// action
		Account confirm = repository.save(bar);

		// check
		assertTrue(confirm != null);
		assertEquals(bar.getEmail(), confirm.getEmail());

	}

	@Test
	public void update() {

		// prepare
		foo.setEmail("fooUpdate@email.com");

		// action
		Account confirm = repository.update(foo);

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals(foo.getEmail(), confirm.getEmail());
		assertThat("failure - expected has email updated", confirm, hasProperty("email", is(foo.getEmail())));

	}

	@Test
	public void findOne() {

		// prepare

		// action
		Account confirm = repository.findOne(foo.getId());

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected same id", foo.getId(), confirm.getId());
	}

	@Test
	public void findOneNotFound() {

		// prepare
		int id = Integer.MAX_VALUE;

		// action
		Account confirm = repository.findOne(id);

		// check
		assertTrue("failure - expected null", confirm == null);
	}

	@Test
	public void findAll() {

		// prepare

		// action
		Iterable<Account> iterableConfirm = repository.findAll();
		List listConfirm = Lists.newArrayList(iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void count() {

		// prepare

		// action
		long count = repository.count();

		// check
		assertTrue("failure - expected count > 0", count > 0);
	}

	@Test
	public void delete() {

		// prepare

		// action
		repository.delete(foo.getId());

		Account confirm = repository.findOne(foo.getId());

		// check
		assertTrue("failure - expected null", confirm == null);

	}

}
