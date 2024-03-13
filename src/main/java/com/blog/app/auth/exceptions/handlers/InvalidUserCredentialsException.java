package com.blog.app.auth.exceptions.handlers;

public class InvalidUserCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidUserCredentialsException() {
	}

	public InvalidUserCredentialsException(String msg) {
		super(msg);
	}
}
