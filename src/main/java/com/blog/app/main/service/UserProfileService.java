package com.blog.app.main.service;

import com.blog.app.main.dto.request.UserProfileUpdateRequest;
import com.blog.app.main.dto.response.UserProfileResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface UserProfileService {

	UserProfileResponse updateUserProfile(HttpServletRequest servletRequest,
			@Valid UserProfileUpdateRequest profileUpdateRequest);

	UserProfileResponse getUserProfile(HttpServletRequest servletRequest);

}
