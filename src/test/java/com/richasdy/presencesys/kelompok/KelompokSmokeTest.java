package com.richasdy.presencesys.kelompok;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.richasdy.presencesys.kelompok.KelompokController;
import com.richasdy.presencesys.kelompok.KelompokRepository;
import com.richasdy.presencesys.kelompok.KelompokService;
import com.richasdy.presencesys.kelompok.KelompokServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KelompokSmokeTest {
	
	@Autowired
	private KelompokRepository groupRepository;

	@Autowired
	private KelompokController groupController;

	@Autowired
	private KelompokService groupService;
	
	@Autowired
	private KelompokServiceImpl groupServiceImpl;
	
	
	
	
	@Test
	public void contexLoadsGroupRepository() throws Exception {
		assertThat(groupRepository).isNotNull();
	}

	@Test
	public void contexLoadsGroupController() throws Exception {
		assertThat(groupController).isNotNull();
	}

	@Test
	public void contexLoadsGroupService() throws Exception {
		assertThat(groupService).isNotNull();
	}
	
	@Test
	public void contexLoadsGroupServiceImpl() throws Exception {
		assertThat(groupServiceImpl).isNotNull();
	}

}
