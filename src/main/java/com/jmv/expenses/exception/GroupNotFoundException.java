package com.jmv.expenses.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GroupNotFoundException extends ResponseStatusException {

	private static final long serialVersionUID = -5768775159568154497L;
	
	private static final String DESCRIPTION_404 = "Group not found: ";
	
	public GroupNotFoundException(long id) {
		super(HttpStatus.NOT_FOUND, DESCRIPTION_404.concat(String.valueOf(id)));
	}
	
	public GroupNotFoundException(String name) {
		super(HttpStatus.NOT_FOUND, DESCRIPTION_404.concat(String.valueOf(name)));
	}
}
