package com.blog.app.auth.exceptions;

import org.springframework.http.HttpStatusCode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiError {
	private HttpStatusCode status;
	private int code;
	private Long timestamp = System.currentTimeMillis();
	private String message;
	@JsonIgnore
	private String debugMessage;
	private Object details;

	public ApiError(HttpStatusCode status) {
		super();
		this.status = status;
		this.code = status.value();
	}

	public ApiError(HttpStatusCode status, String message) {
		super();
		this.status = status;
		this.code = status.value();
		this.message = message;
	}

	public ApiError(HttpStatusCode status, String message, Object details) {
		super();
		this.status = status;
		this.code = status.value();
		this.message = message;
		this.details = details;
	}

	public ApiError(HttpStatusCode status, String message, Throwable ex) {
		this();
		this.status = status;
		this.code = status.value();
		this.message = message;
		this.debugMessage = ex.getMessage();
	}

	public ApiError(HttpStatusCode status, String message, Throwable ex, Object details) {
		this();
		this.status = status;
		this.code = status.value();
		this.message = message;
		this.details = details;
		this.debugMessage = ex.getMessage();
	}
}
