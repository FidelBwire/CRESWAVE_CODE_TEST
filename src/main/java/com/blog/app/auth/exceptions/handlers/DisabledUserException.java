package com.blog.app.auth.exceptions.handlers;

public class DisabledUserException extends RuntimeException {

	private static final long serialVersionUID = 1643021530381014877L;

	public DisabledUserException() {
		this("Disabled User");
	}

	public DisabledUserException(String message) {
		super(message);
	}
}
