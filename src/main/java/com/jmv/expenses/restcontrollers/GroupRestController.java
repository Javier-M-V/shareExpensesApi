package com.jmv.expenses.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.jmv.expenses.models.Group;

import com.jmv.expenses.services.IGroupService;

@RequestMapping("/group")
@RestController
public class GroupRestController {
	
	@Autowired
	IGroupService groupService;
	
	@PostMapping(value = "/post", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void post(@RequestBody Group group){
		
		groupService.save(group);
	}
}
