package com.javier.expenses.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
	private GroupRepository repo;

	static Group group;
	
	static Iterable<Group> iterableGroup;
	
	@BeforeEach
	public void before() {
		
		group = new Group();
		group.setId(1L);
		List<Person> personList = new ArrayList<>();
		personList.add(new Person());
		group.setPersonsList(personList);
		
		iterableGroup = new ArrayList<Group>();
	}
	
	@Test
	public void test_findById_returns_one() {
		
		Mockito.when(repo.findById(Mockito.anyLong())).thenReturn(Optional.of(group));
		
		groupService.findById(1L);
		
		verify(repo, times(1)).findById(Mockito.anyLong());
	}
	
	@Test
	public void test_findByIdName() {
		
		Mockito.when(repo.findByNameGroup(Mockito.anyString())).thenReturn(iterableGroup);
		
		groupService.findByNameGroup("friends");
		
		verify(repo, times(1)).findByNameGroup(Mockito.anyString());
	}
}
