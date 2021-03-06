package com.jmv.expenses.services.api;

import java.util.Optional;

import com.jmv.expenses.models.Group;

public interface IGroupService {
	
	public Optional<Group> findById(Long id); 
	
	public Iterable<Group> findByNameGroup(String name); 
	
	public void save(Group group);

}
