package com.richasdy.presencesys.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.text.ParseException;
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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.richasdy.presencesys.AbstractTest;
import com.richasdy.presencesys.domain.Account;
import com.richasdy.presencesys.repository.AccountRepository;
import com.richasdy.presencesys.service.AccountService;

@Transactional
public class AccountRepositoryTest extends AbstractTest {

	// this is integration test
	// AccountRestRepository langsung berkomunikasi dengan api
	// repository dont have validation, controller have
	// independent test with initialization db dml
	// no change db status, no left some garbage data in db

	@Autowired
	private AccountRepository repository;

	private Account foo;

	@Before
	public void init() {

		foo = new Account();
		foo.setEmail("foo@email.com");
		foo.setPhone("000000000000");
		foo.setUsername("fooUsername");
		foo.setPassword("fooPassword");
		foo.setCreatedAt(new Date());

		foo = repository.save(foo);
	}

	@Test
	public void findDistinctAccountByEmailOrPhoneOrUsernameOrNoteOrPermissionsOrActivationCodeOrPersistCodeOrResetPasswordCode() {

		// prepare

		// action
		Iterable<Account> iterableConfirm = repository
				.findDistinctAccountByEmailOrPhoneOrUsernameOrNoteOrPermissionsOrActivationCodeOrPersistCodeOrResetPasswordCode(
						foo.getEmail(), foo.getEmail(), foo.getEmail(), foo.getEmail(), foo.getEmail(), foo.getEmail(),
						foo.getEmail(), foo.getEmail());

		List listConfirm = Lists.newArrayList(iterableConfirm);

		// System.out.println("@searchString : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void findDistinctAccountById() {

		// prepare

		// action
		Iterable<Account> iterableConfirm = repository.findDistinctAccountById(foo.getId());
		List listConfirm = Lists.newArrayList(iterableConfirm);

		// System.out.println("@searchInt : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	@Test
	public void findDistinctAccountByActivated() {

		// prepare

		// action
		Iterable<Account> iterableConfirm = repository.findDistinctAccountByActivated(true);
		List listConfirm = Lists.newArrayList(iterableConfirm);

		// System.out.println("@searchBoolean : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);

	}

	// @Test
	// public void
	// findDistinctAccountByActivatedAtOrLastLoginOrCreatedAtOrUpdatedAtOrDeletedAt()
	// {
	//
	// // prepare
	// Date now = new Date();
	//
	// // System.out.println("@searchDate : " + now);
	//
	// // action
	// Iterable<Account> iterableConfirm = repository
	// .findDistinctAccountByActivatedAtOrLastLoginOrCreatedAtOrUpdatedAtOrDeletedAt(now,
	// now, now, now, now);
	// List listConfirm = Lists.newArrayList(iterableConfirm);
	//
	// // System.out.println("@searchDate : " + iterableConfirm);
	//
	// // check
	// assertTrue("failure - expected not null", iterableConfirm != null);
	// assertTrue("failure - expected size > 0", listConfirm.size() > 0);
	// }

	@Test
	public void findDistinctAccountByActivatedAtBetween() {

		// prepare
		Date firstTimeToday = new Date();
		Date lastTimeToday = new Date();

		System.out.println("@searchDate : " + firstTimeToday);
		System.out.println("@searchDate : " + lastTimeToday);

		firstTimeToday.setHours(00);
		firstTimeToday.setMinutes(00);
		firstTimeToday.setSeconds(00);

		lastTimeToday.setHours(23);
		lastTimeToday.setMinutes(59);
		lastTimeToday.setSeconds(59);

		System.out.println("@searchDate : " + firstTimeToday);
		System.out.println("@searchDate : " + lastTimeToday);

		// action
		Iterable<Account> iterableConfirm = repository.findDistinctAccountByCreatedAtBetween(firstTimeToday,
				lastTimeToday);
		List listConfirm = Lists.newArrayList(iterableConfirm);

		System.out.println("@searchDate : " + iterableConfirm);

		// check
		assertTrue("failure - expected not null", iterableConfirm != null);
		assertTrue("failure - expected size > 0", listConfirm.size() > 0);
	}


}
