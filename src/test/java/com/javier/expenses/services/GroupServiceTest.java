package com.javier.expenses.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Person;
import com.jmv.expenses.repository.GroupRepository;
import com.jmv.expenses.services.impl.GroupService;

import junit.framework.TestCase;

@RunWith(PowerMockRunner.class)
public class GroupServiceTest extends TestCase {
	
	@InjectMocks
	private GroupService groupService;
	
	@Mock
	private GroupRepository dao;

	static Group group;
	
	@Before
	public void before() {
		
		group = new Group();
		group.setId(1L);
		List<Person> personList = new ArrayList<>();
		personList.add(new Person());
		group.setPersonsList(personList);
	}
	
	@Test
	public void test_findById_returns_one() {
		
		Mockito.when(dao.findById(Mockito.anyLong())).thenReturn(Optional.of(group));
		
		Optional<Group> result = groupService.findById(1L);
		
		assertNotNull(result.get());
	}
}
