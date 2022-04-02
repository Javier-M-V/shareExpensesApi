package com.jmv.expenses.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmv.expenses.models.Group;
import com.jmv.expenses.repository.GroupRepository;
import com.jmv.expenses.services.api.IGroupService;

@Service
public class GroupService implements IGroupService{
	
	@Autowired
	private GroupRepository groupRepo;

	@Override
	public Optional<Group> findById(Long id) {
		
		return groupRepo.findById(id);
	}
	
	public void save(Group group) {
		
		groupRepo.save(group);
	}

	@Override
	public List<Group> findByNameGroup(String name) {
		
		return StreamSupport.stream(groupRepo.findByNameGroup(name).spliterator(), false).collect(Collectors.toList());
		
	}
}
