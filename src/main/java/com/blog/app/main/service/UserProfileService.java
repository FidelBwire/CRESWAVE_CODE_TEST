package com.blog.app.main.service;

import com.blog.app.main.dto.request.UserProfileCreationRequest;
import com.blog.app.main.dto.response.UserProfileCreationResponse;

import jakarta.validation.Valid;

public interface UserProfileService {

	UserProfileCreationResponse createUserProfile(@Valid UserProfileCreationRequest profileCreationRequest);

}
