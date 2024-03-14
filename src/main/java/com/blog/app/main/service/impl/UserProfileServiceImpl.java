package com.blog.app.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.main.dto.request.UserProfileCreationRequest;
import com.blog.app.main.dto.response.UserProfileCreationResponse;
import com.blog.app.main.entity.UserProfile;
import com.blog.app.main.repository.UserProfileRepository;
import com.blog.app.main.service.UserProfileService;

import jakarta.validation.Valid;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileRepository profileRepository;

	@Override
	public UserProfileCreationResponse createUserProfile(@Valid UserProfileCreationRequest profileCreationRequest) {
		UserProfile userProfile = UserProfile.builder().fullName(profileCreationRequest.getFullName()).build();
		UserProfile newUserProfile = profileRepository.save(userProfile);
		return createUserProfileResponse(newUserProfile);
	}

	private UserProfileCreationResponse createUserProfileResponse(UserProfile profile) {
		return UserProfileCreationResponse.builder().fullName(profile.getFullName()).build();
	}
}
