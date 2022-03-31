package com.jmv.expenses.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.dao.PersonRepository;
import com.jmv.expenses.models.Person;

@Service
public class PersonService implements IPersonService{

	@Autowired
	private PersonRepository personDao;
	
	@Override
	public Person findById(Long id) {
		
		return personDao.findById(id).get();
	}

	@Override
	public void save(Person person) {
		
		personDao.save(person);
	}
	
	@Override
	public List<Person> findAll() {
		
		return StreamSupport.stream(personDao.findAll().spliterator(), false)
	    .collect(Collectors.toList());
	}
}
