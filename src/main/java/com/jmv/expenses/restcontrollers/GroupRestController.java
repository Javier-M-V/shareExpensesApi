package com.jmv.expenses.restcontrollers;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmv.expenses.exception.GroupNotFoundException;
import com.jmv.expenses.models.Group;
import com.jmv.expenses.services.api.IGroupService;


@RequestMapping("/group")
@RestController
public class GroupRestController {
	
	@Autowired
	IGroupService groupService;
	
	@PostMapping(value = "/", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void post(@RequestBody Group group){
		
		groupService.save(group);
	}
	
	@GetMapping(value = "/")
	public EntityModel<Group> getById(@RequestParam long id) {
		
		Optional<Group> group = groupService.findById(id);

		return EntityModel.of(group.orElseThrow(()-> new GroupNotFoundException(id)));	
	}
	
	@GetMapping(value = "/{name}")
	public CollectionModel<Group> getByName(@PathVariable String name) {
		
		return CollectionModel.of(groupService.findByNameGroup(name));	
	}
}
