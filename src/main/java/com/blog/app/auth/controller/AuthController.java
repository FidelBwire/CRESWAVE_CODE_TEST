package com.blog.app.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.auth.dto.request.SignInRequest;
import com.blog.app.auth.dto.request.SignUpRequest;
import com.blog.app.auth.dto.response.SignInResponse;
import com.blog.app.auth.service.UserAuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserAuthService userAuthService;

	@PostMapping("/signin")
	public ResponseEntity<SignInResponse> signIn(@Valid @RequestBody SignInRequest request) {
		SignInResponse signInResponse = userAuthService.signIn(request);
		return new ResponseEntity<>(signInResponse, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<SignInResponse> signup(@Valid @RequestBody SignUpRequest request) {
		SignInResponse signInResponse = userAuthService.signUp(request);
		return new ResponseEntity<>(signInResponse, HttpStatus.OK);
	}

}