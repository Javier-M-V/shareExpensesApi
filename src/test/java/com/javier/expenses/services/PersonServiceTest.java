package com.javier.expenses.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jmv.expenses.dto.PersonGroupDTO;
import com.jmv.expenses.exception.GroupNotFoundException;
import com.jmv.expenses.exception.PersonNotFoundException;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Person;
import com.jmv.expenses.repository.GroupRepository;
import com.jmv.expenses.repository.PersonRepository;
import com.jmv.expenses.services.impl.PersonService;

import junit.framework.TestCase;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest extends TestCase {
	
	@InjectMocks
	private PersonService personService;
	
	@Mock
	private PersonRepository personRepo;
	
	@Mock
	private GroupRepository groupRepo;
	
	static Optional<Person> opPerson;
	
	static Optional<Person> opPersonNull = Optional.ofNullable(null);
	
	static Optional<Group> opGroupNull = Optional.ofNullable(null);
	
	static Person person;
	
	static Group group;
	
	static Optional<Group> opGroup;
	
	static List<Person> listPersons;
	
	@BeforeAll
	static void setup() {
		
		group = new Group();
		person = new Person();
		person.setId(33L);
		person.setGroupList(new ArrayList<Group>());
		group.setPersonsList(Arrays.asList(person));
		opPerson = Optional.of(person);
		opGroup = Optional.of(group);
		
		listPersons = new ArrayList<>();
		listPersons.add(person);
	}
	
	@Test
	void test_find_by_id_finds_one() {
		
		Mockito.when(personRepo.findById(Mockito.anyLong())).thenReturn(opPerson);
		
		try {
			Person result = personService.findById(2L);
			
			assertSame(result, person);
			verify(personRepo, times(1)).findById(Mockito.anyLong());
			
		}catch (PersonNotFoundException e) {
			fail();
		}
	}
	
	@Test
	void test_find_by_id_throws_PersonNotFound() {
		
		Mockito.when(personRepo.findById(Mockito.anyLong())).thenReturn(opPersonNull);
		
		try {
			personService.findById(2L);
			fail();
			
		}catch (PersonNotFoundException e) {
			verify(personRepo, times(1)).findById(Mockito.anyLong());
		}
	}
	
	@Test
	void test_save_calls_repository() {
		
		personService.save(new Person());
		
		verify(personRepo, times(1)).save(Mockito.any(Person.class));
	}
	
	@Test
	void test_findAllFromGroup_finds_group_and_persons() {
		
		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(opGroup);
		Mockito.when(personRepo.findAllById(Mockito.anyList())).thenReturn(listPersons);
		
		;
		try {
			
		  List<Person> result = personService.findAllFromGroup(1L);
		  
		  verify(personRepo, times(1)).findAllById(Arrays.asList(33L));
		  verify(groupRepo, times(1)).findById(1L);
		  assertSame(person, result.get(0));
		  
		}catch (GroupNotFoundException e) {
			fail();
		}
	}
	
	@Test
	void test_findAllFromGroup_throws_GroupNotFoundException() {
		
		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(opGroupNull);
	
		try {
			
		  personService.findAllFromGroup(14L);
		  fail();
		  
		}catch (GroupNotFoundException e) {
			verify(groupRepo, times(1)).findById(14L);
		}
	}
	
	@Test
	void test_addPersonToGroup_calls_save() {
		
		Mockito.when(personRepo.findById(Mockito.anyLong())).thenReturn(opPerson);
		Mockito.when(groupRepo.findById(Mockito.anyLong())).thenReturn(opGroup);
		
		PersonGroupDTO dto = new PersonGroupDTO();
		
		dto.setGroupId(1L);
		dto.setPersonId(2L);
		
		personService.addPersonToGroup(dto);
		
		verify(personRepo, times(1)).save(person);
		assertEquals(opGroup.get(),person.getGroupList().get(0));	
	}
}
