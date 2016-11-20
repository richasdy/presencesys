package com.richasdy.presencesys.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class UtilTest {

	@Test
	public void isValidDateTrue() {

		Boolean valid = Util.isValidDate("2016-11-20");
		assertTrue("failure - expected true", valid);

	}

	@Test
	public void isValidDateTrueString() {

		Boolean valid = Util.isValidDate("2016-11-20alsdalksjdl");
		assertTrue("failure - expected true", valid);

	}

	@Test
	public void isValidDateFalse() {

		Boolean valid = Util.isValidDate("2016-11-200");
		assertFalse("failure - expected false", valid);

	}

	@Test
	public void stringToDateTrue() {

		Date valid = Util.stringToDate("2016-11-20");
		assertTrue("failure - expected not null", valid!=null);

	}

	@Test
	public void stringToDateFalse() {

		Date valid = Util.stringToDate("2016-11-s20");
		assertTrue("failure - expected null", valid == null);

	}

}
