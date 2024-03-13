package com.blog.app.auth.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.app.auth.exceptions.ApiError;

@RestControllerAdvice
public class RestCustomExceptionHandler extends RestExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception) {
		return buildResponseEntity(HttpStatus.NOT_FOUND, exception);
	}

	@ExceptionHandler(value = DisabledUserException.class)
	public ResponseEntity<Object> handleDisabledUserException(DisabledUserException exception) {
		return buildResponseEntity(HttpStatus.UNAUTHORIZED, exception);
	}

	@ExceptionHandler(value = ForbiddenAccessException.class)
	public ResponseEntity<Object> handleForbiddenAccessException(ForbiddenAccessException exception) {
		return buildResponseEntity(HttpStatus.UNAUTHORIZED, exception);
	}

	@ExceptionHandler(value = ForbiddenActionException.class)
	public ResponseEntity<Object> handleForbiddenActionException(ForbiddenActionException exception) {
		return buildResponseEntity(HttpStatus.UNAUTHORIZED, exception);
	}

	@ExceptionHandler(value = InvalidUserCredentialsException.class)
	public ResponseEntity<Object> handleInvalidUserCredentialsException(InvalidUserCredentialsException exception) {
		return buildResponseEntity(HttpStatus.UNAUTHORIZED, exception);
	}

	@ExceptionHandler(value = JwtTokenMalformedException.class)
	public ResponseEntity<Object> handleJwtTokenMalformedException(JwtTokenMalformedException exception) {
		return buildResponseEntity(HttpStatus.UNAUTHORIZED, exception);
	}

	@ExceptionHandler(value = JwtTokenMissingException.class)
	public ResponseEntity<Object> handleJwtTokenMissingException(JwtTokenMissingException exception) {
		return buildResponseEntity(HttpStatus.UNAUTHORIZED, exception);
	}

	protected ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, RuntimeException exception) {
		ApiError apiError = new ApiError(httpStatus, exception.getLocalizedMessage());
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
