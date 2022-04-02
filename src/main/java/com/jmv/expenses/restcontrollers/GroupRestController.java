package com.jmv.expenses.restcontrollers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmv.expenses.exception.GroupNotFoundException;
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
	
	@GetMapping(value = "/")
	public EntityModel<Group> getById(@RequestParam long id) {
		
		Optional<Group> group = groupService.findById(id);

		if(group.isPresent()) {
			
			return EntityModel.of(group.get());
		}
		else {
			throw new GroupNotFoundException(id);
		}		
	}
}
