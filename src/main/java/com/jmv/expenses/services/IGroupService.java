package com.jmv.expenses.services;

import com.jmv.expenses.models.Group;

public interface IGroupService {
	
	public Group findById(Long id); 
	
	public void save(Group group);

}
