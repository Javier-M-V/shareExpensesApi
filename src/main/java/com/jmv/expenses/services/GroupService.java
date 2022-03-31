package com.jmv.expenses.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.dao.GroupRepository;
import com.jmv.expenses.models.Group;

@Service
public class GroupService implements IGroupService{
	
	@Autowired
	private GroupRepository groupDao;

	@Override
	public Group findById(Long id) {
		
		return groupDao.findById(id).get();
	}
	
	public void save(Group group) {
		
		groupDao.save(group);
	}
}
