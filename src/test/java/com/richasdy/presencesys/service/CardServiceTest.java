package com.richasdy.presencesys.service;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.richasdy.presencesys.AbstractTest;
import com.richasdy.presencesys.domain.Card;
import com.richasdy.presencesys.repository.AccountRepository;
import com.richasdy.presencesys.service.CardService;

@Transactional
public class CardServiceTest extends AbstractTest {

	// this is integration test
	// service dont have validation, controller have
	// independent test with initialization db dml
	// no change db status, no left some garbage data in db

	@Autowired
	private CardService service;
	private Card foo;

	@Before
	public void init() {
		foo = new Card();
		foo.setCardNumber("0000000000");

		foo = service.save(foo);
	}

	@Test
	public void save() {

		// prepare
		Card bar = new Card();
		bar.setCardNumber("9999999999");

		long countBefore = service.count();

		// action
		Card confirm = service.save(bar);

		long countAfter = service.count();

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected right count", countAfter, countBefore + 1);
		assertEquals("failure - expected same value", bar.getCardNumber(), confirm.getCardNumber());

	}

	@Test(expected = ConstraintViolationException.class)
	public void saveValidationErrorEmptyField() {

		// prepare
		Card bar = new Card();
		// bar.setCardNumber("9999999999");

		// action
		Card confirm = service.save(bar);

		// check

	}

	@Test
	public void update() {

		// prepare
		foo.setCardNumber("000000001");

		// action
		Card confirm = service.update(foo);

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected same value", foo.getCardNumber(), confirm.getCardNumber());
		assertThat("failure - expected same value updated", confirm,
				hasProperty("cardNumber", is(foo.getCardNumber())));

	}

	@Test(expected = NullPointerException.class)
	public void updateNotFound() {

		// prepare
		Card notFound = service.findOne(Integer.MAX_VALUE);

		// action
		Card confirm = service.update(notFound);

		// check

	}

	@Test(expected = ConstraintViolationException.class)
	public void updateValidationErrorEmptyField() {

		// prepare
		Card bar = new Card();
		bar.setId(Integer.MAX_VALUE);
		// bar.setCardNumber("9999999999");
		bar.setCreatedAt(new Date());

		// action
		// kalau lolos menjadi save
		Card confirm = service.update(bar);

		// check

	}

	@Test
	public void findOne() {

		// prepare

		// action
		Card confirm = service.findOne(foo.getId());

		// check
		assertTrue("failure - expected not null", confirm != null);
		assertEquals("failure - expected same id", foo.getId(), confirm.getId());
	}

	@Test
	public void findOneNotFound() {

		// prepare
		int id = Integer.MAX_VALUE;

		// action
		Card confirm = service.findOne(id);

		// check
		assertTrue("failure - expected null", confirm == null);
	}

	@Test
	public void findAll() {

		// prepare

		// action
		Iterable<Card> iterableConfirm = service.findAll();
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

		Card confirm = service.findOne(foo.getId());

		// check
		assertTrue("failure - expected null", confirm == null);

	}

	@Test
	public void deleteSoft() {

		// prepare

		// action
		Card confirm = service.deleteSoft(foo.getId());

		// check
		assertTrue("failure - expected not null", confirm.getDeletedAt() != null);

	}

}
