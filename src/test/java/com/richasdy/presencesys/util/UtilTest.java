package com.richasdy.presencesys.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isNotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
		assertTrue("failure - expected not null", valid != null);

	}

	@Test
	public void stringToDateFalse() {

		Date valid = Util.stringToDate("2016-11-s20");
		assertTrue("failure - expected null", valid == null);

	}

	@Test
	public void getTimeZone() {

		System.out.println(TimeZone.getDefault());
		// Date valid = Util.stringToDate("2016-11-s20");
		// assertTrue("failure - expected null", valid == null);

	}

	// @Test
	public void datetry() throws ParseException {

		Date sekarang = new Date();

		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Date setelah = new Date();

		System.out.println("COMPARE");
		System.out.println("sekarang : " + sekarang);
		System.out.println("setelah : " + setelah);
		System.out.println("operation : " + setelah.compareTo(sekarang));
		System.out.println("operation : " + sekarang.compareTo(setelah));

		System.out.println("DATE FORMAT");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date parsingTanggal = format.parse("2016-01-13");
		System.out.println("sekarang : " + format.format(sekarang));
		System.out.println("parsingTanggal : " + parsingTanggal);
		System.out.println("parsingTanggal : " + format.format(parsingTanggal));

		System.out.println("COMPARE WITH DATE FROMAT");
		Date parsingTanggalsekarang = format.parse("2017-01-31");
		System.out.println(parsingTanggalsekarang);
		System.out.println(sekarang);
		System.out.println("parsing");
		System.out.println(format.format(parsingTanggalsekarang));
		System.out.println(format.format(sekarang));

		System.out.println(parsingTanggalsekarang.equals(format.format(sekarang)));
		System.out.println(format.format(parsingTanggalsekarang).equals(format.format(sekarang)));

	}

}
