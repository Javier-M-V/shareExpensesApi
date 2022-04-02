package com.jmv.expenses.services;

import java.util.Optional;

import com.jmv.expenses.models.Group;

public interface IGroupService {
	
	public Optional<Group> findById(Long id); 
	
	public void save(Group group);

}
