package com.javier.expenses.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Person;
import com.jmv.expenses.repository.GroupRepository;
import com.jmv.expenses.services.impl.GroupService;

import junit.framework.TestCase;


@ExtendWith(MockitoExtension.class)
public class GroupServiceTest extends TestCase {
	
	@InjectMocks
	private GroupService groupService;
	
	@Mock
	private GroupRepository groupRepo;

	static Group group;
	
	static Iterable<Group> iterableGroup;
	
	@BeforeAll
	static void setup() {
		
		group = new Group();
		group.setId(1L);
		List<Person> personList = new ArrayList<>();
		personList.add(new Person());
		group.setPersonsList(personList);
		
		iterableGroup = new ArrayList<Group>();
	}
	
	@Test
	void test_findById_returns_one() {
		
		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(group));
		
		groupService.findById(1L);
		
		verify(groupRepo, times(1)).findById(Mockito.anyLong());
	}
	
	@Test
	void test_findByIdName() {
		
		Mockito.when(groupRepo.findByNameGroup(Mockito.anyString())).thenReturn(iterableGroup);
		
		groupService.findByNameGroup("friends");
		
		verify(groupRepo, times(1)).findByNameGroup(Mockito.anyString());
	}
	
	@Test
	void test_save_calls_repo() {
		
		groupService.save(group);
		
		verify(groupRepo, times(1)).save(Mockito.any(Group.class));
	}
}
