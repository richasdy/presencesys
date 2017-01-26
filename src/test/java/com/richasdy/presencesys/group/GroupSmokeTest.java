package com.richasdy.presencesys.group;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.richasdy.presencesys.group.GroupController;
import com.richasdy.presencesys.group.GroupRepository;
import com.richasdy.presencesys.group.GroupService;
import com.richasdy.presencesys.group.GroupServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupSmokeTest {
	
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private GroupController groupController;

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupServiceImpl groupServiceImpl;
	
	
	
	
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
