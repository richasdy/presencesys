package com.richasdy.presencesys.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.richasdy.presencesys.schedule.ScheduleController;
import com.richasdy.presencesys.schedule.ScheduleRepository;
import com.richasdy.presencesys.schedule.ScheduleService;
import com.richasdy.presencesys.schedule.ScheduleServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduleSmokeTest {
	
	@Autowired
	private ScheduleRepository scheduleRepository;

	@Autowired
	private ScheduleController scheduleController;

	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private ScheduleServiceImpl scheduleServiceImpl;
	
	
	
	
	@Test
	public void contexLoadsScheduleRepository() throws Exception {
		assertThat(scheduleRepository).isNotNull();
	}

	@Test
	public void contexLoadsScheduleController() throws Exception {
		assertThat(scheduleController).isNotNull();
	}

	@Test
	public void contexLoadsScheduleService() throws Exception {
		assertThat(scheduleService).isNotNull();
	}
	
	@Test
	public void contexLoadsScheduleServiceImpl() throws Exception {
		assertThat(scheduleServiceImpl).isNotNull();
	}

}
