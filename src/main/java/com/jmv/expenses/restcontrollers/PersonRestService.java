package com.jmv.expenses.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jmv.expenses.models.Person;
import com.jmv.expenses.services.IPersonService;

@RequestMapping("/person")
@RestController
public class PersonRestService {
	
	@Autowired
	private IPersonService personService;
	
	@GetMapping(value = "/all")
	public @ResponseBody List<Person> getAll() {
		
		return personService.findAll();
	}
	
	@GetMapping(value = "/get")
	public @ResponseBody Person getById(@RequestParam long id) {
		
		return personService.findById(id);
	}
	
	
}
