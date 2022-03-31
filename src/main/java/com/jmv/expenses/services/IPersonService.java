package com.jmv.expenses.services;

import java.util.List;

import com.jmv.expenses.models.Person;

public interface IPersonService {
	
	public Person findById(Long id); 
	
	public void save(Person person);
	
	public List<Person> findAll();
	
}
