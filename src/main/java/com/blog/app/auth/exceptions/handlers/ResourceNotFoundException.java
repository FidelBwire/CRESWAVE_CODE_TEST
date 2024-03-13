package com.blog.app.auth.exceptions.handlers;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 4666934359340669562L;

	public ResourceNotFoundException() {
		this("Resource Not Found");
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
