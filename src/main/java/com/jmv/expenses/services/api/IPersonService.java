package com.jmv.expenses.services.api;

import java.util.List;

import com.jmv.expenses.dto.PersonGroupDTO;
import com.jmv.expenses.models.Person;

public interface IPersonService {
	
	public Person findById(Long id); 
	
	public void save(Person person);

	public List<Person> findAll();

	public void addPersonToGroup(PersonGroupDTO personGroup);

	List<Person> findAllFromGroup(Long id);
}
