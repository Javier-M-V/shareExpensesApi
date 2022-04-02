package com.jmv.expenses.repository;

import org.springframework.data.repository.CrudRepository;

import com.jmv.expenses.models.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
	
}
