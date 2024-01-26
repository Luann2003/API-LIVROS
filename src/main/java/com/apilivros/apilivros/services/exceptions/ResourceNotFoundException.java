package com.apilivros.apilivros.services.exceptions;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {

	 public ResourceNotFoundException(String msg) {
	        super(msg);
	    }
}
