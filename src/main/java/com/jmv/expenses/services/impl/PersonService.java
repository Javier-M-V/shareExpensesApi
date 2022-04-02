package com.jmv.expenses.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.dto.PersonGroupDTO;
import com.jmv.expenses.exception.GroupNotFoundException;
import com.jmv.expenses.exception.PersonNotFoundException;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.models.Person;
import com.jmv.expenses.repository.GroupRepository;
import com.jmv.expenses.repository.PersonRepository;
import com.jmv.expenses.services.api.IPersonService;

@Service
public class PersonService implements IPersonService {

	@Autowired
	private PersonRepository personRepo;

	@Autowired
	private GroupRepository groupRepo;

	@Override
	public Person findById(Long id) {

		Optional<Person> person = personRepo.findById(id);

		if(person.isPresent()) {
			
			return person.get();
		}
		else {
			throw new PersonNotFoundException(id);
		} 
	}

	@Override
	public void save(Person person) {

		personRepo.save(person);
	}

	@Override
	public List<Person> findAll() {

		return StreamSupport.stream(personRepo.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public List<Person> findAllFromGroup(Long id) {

		Optional<Group> group = groupRepo.findById(id);
		
		if(group.isPresent()) {
			
			List<Long> ids = group.get().getPersonsList().stream()
					.map(Person::getId).collect(Collectors.toList());

			return StreamSupport.stream(personRepo.findAllById(ids).spliterator(), false).collect(Collectors.toList());
		}
		else {
			throw new GroupNotFoundException(id);
		}
	}

	@Override
	public void addPersonToGroup(PersonGroupDTO personGroup) {

		Person person = this.findById(personGroup.getPersonId());

		Group group = groupRepo.findById(personGroup.getGroupId()).get();

		person.getGroupList().add(group);

		personRepo.save(person);
	}
}
