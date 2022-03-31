package com.jmv.expenses.dao;

import org.springframework.data.repository.CrudRepository;

import com.jmv.expenses.models.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
	
}
