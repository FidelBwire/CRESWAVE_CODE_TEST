package com.blog.app.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.main.dto.request.UserProfileUpdateRequest;
import com.blog.app.main.dto.response.UserProfileResponse;
import com.blog.app.main.service.UserProfileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;

	@GetMapping
	public ResponseEntity<UserProfileResponse> getUserProfile(HttpServletRequest servletRequest) {
		UserProfileResponse profileResponse = userProfileService.getUserProfile(servletRequest);
		return new ResponseEntity<>(profileResponse, HttpStatus.OK);
	}

	@PutMapping()
	public ResponseEntity<UserProfileResponse> updateUserProfile(HttpServletRequest servletRequest,
			@Valid @RequestBody UserProfileUpdateRequest profileUpdateRequest) {
		UserProfileResponse profileResponse = userProfileService.updateUserProfile(servletRequest,
				profileUpdateRequest);
		return new ResponseEntity<>(profileResponse, HttpStatus.OK);
	}
}
