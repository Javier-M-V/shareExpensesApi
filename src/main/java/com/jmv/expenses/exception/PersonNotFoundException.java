package com.jmv.expenses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PersonNotFoundException extends ResponseStatusException {
	
	private static final long serialVersionUID = 1654077752010450594L;

	public PersonNotFoundException(long id) {
		super(HttpStatus.NOT_FOUND, "Person not found ".concat(String.valueOf(id)));
	}
}
