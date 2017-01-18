package com.richasdy.presencesys.machine;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.richasdy.presencesys.machine.MachineController;
import com.richasdy.presencesys.machine.MachineRepository;
import com.richasdy.presencesys.machine.MachineService;
import com.richasdy.presencesys.machine.MachineServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MachineSmokeTest {
	
	@Autowired
	private MachineRepository machineRepository;

	@Autowired
	private MachineController machineController;

	@Autowired
	private MachineService machineService;
	
	@Autowired
	private MachineServiceImpl machineServiceImpl;
	
	
	
	
	@Test
	public void contexLoadsMachineRepository() throws Exception {
		assertThat(machineRepository).isNotNull();
	}

	@Test
	public void contexLoadsMachineController() throws Exception {
		assertThat(machineController).isNotNull();
	}

	@Test
	public void contexLoadsMachineService() throws Exception {
		assertThat(machineService).isNotNull();
	}
	
	@Test
	public void contexLoadsMachineServiceImpl() throws Exception {
		assertThat(machineServiceImpl).isNotNull();
	}

}
