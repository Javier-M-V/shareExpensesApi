package com.jmv.expenses.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmv.expenses.dto.PersonGroupDTO;
import com.jmv.expenses.models.Person;
import com.jmv.expenses.services.api.IPersonService;

@RequestMapping("/person")
@RestController
public class PersonRestController {
	
	@Autowired
	private IPersonService personService;
	
	@GetMapping
	public CollectionModel<Person> getAll() {
		
		return CollectionModel.of(personService.findAll());
	}
	
	@GetMapping(value = "/")
	public EntityModel<Person> getById(@RequestParam long id) {
		
		return EntityModel.of(personService.findById(id));
	}
	
	@PostMapping(value = "/post", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void post(@RequestBody Person person) { 
		
		personService.save(person);
	}
	
	@PutMapping(value = "/group/put", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void put(@RequestBody PersonGroupDTO personGroup) { 
		
		personService.addPersonToGroup(personGroup);	
	}
}
