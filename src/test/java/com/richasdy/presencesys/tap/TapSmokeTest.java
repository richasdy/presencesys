package com.richasdy.presencesys.tap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.richasdy.presencesys.tap.TapController;
import com.richasdy.presencesys.tap.TapRepository;
import com.richasdy.presencesys.tap.TapService;
import com.richasdy.presencesys.tap.TapServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TapSmokeTest {
	
	@Autowired
	private TapRepository tapRepository;

	@Autowired
	private TapController tapController;

	@Autowired
	private TapService tapService;
	
	@Autowired
	private TapServiceImpl tapServiceImpl;
	
	
	
	
	@Test
	public void contexLoadsTapRepository() throws Exception {
		assertThat(tapRepository).isNotNull();
	}

	@Test
	public void contexLoadsTapController() throws Exception {
		assertThat(tapController).isNotNull();
	}

	@Test
	public void contexLoadsTapService() throws Exception {
		assertThat(tapService).isNotNull();
	}
	
	@Test
	public void contexLoadsTapServiceImpl() throws Exception {
		assertThat(tapServiceImpl).isNotNull();
	}

}
