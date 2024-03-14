package com.blog.app.main.service;

import com.blog.app.main.dto.request.UserProfileCreationRequest;
import com.blog.app.main.dto.response.UserProfileResponse;

import jakarta.validation.Valid;

public interface UserProfileService {

	UserProfileResponse createUserProfile(@Valid UserProfileCreationRequest profileCreationRequest);

}
