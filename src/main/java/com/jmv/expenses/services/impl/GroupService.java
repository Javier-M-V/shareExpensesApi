package com.jmv.expenses.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.models.Group;
import com.jmv.expenses.repository.GroupRepository;
import com.jmv.expenses.services.api.IGroupService;

@Service
public class GroupService implements IGroupService{
	
	@Autowired
	private GroupRepository groupDao;

	@Override
	public Optional<Group> findById(Long id) {
		
		return groupDao.findById(id);
	}
	
	public void save(Group group) {
		
		groupDao.save(group);
	}
}
