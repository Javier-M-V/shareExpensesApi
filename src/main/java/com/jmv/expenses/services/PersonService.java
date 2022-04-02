package com.jmv.expenses.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.dao.GroupRepository;
import com.jmv.expenses.dao.PersonRepository;
import com.jmv.expenses.dto.PersonGroupDTO;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Person;

@Service
public class PersonService implements IPersonService{

	@Autowired
	private PersonRepository personRepo;
	
	@Autowired
	private GroupRepository groupRepo;
	
	@Override
	public Person findById(Long id) {
		
		return personRepo.findById(id).get();
	}

	@Override
	public void save(Person person) {
		
		personRepo.save(person);
	}
	
	@Override
	public List<Person> findAll() {
		
		return StreamSupport.stream(personRepo.findAll().spliterator(), false)
	    .collect(Collectors.toList());
	}

	@Override
	public void addPersonToGroup(PersonGroupDTO personGroup) {
		
		Person person = this.findById(personGroup.getPersonId());
		
		Group group = groupRepo.findById(personGroup.getGroupId()).get();
		
		person.getGroupList().add(group);
		
		personRepo.save(person);
	}

	@Override
	public void update(Person person, Long id) {
		
	}
}
