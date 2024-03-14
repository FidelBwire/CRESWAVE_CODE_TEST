package com.blog.app.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.auth.entity.User;
import com.blog.app.auth.repository.UserRepository;
import com.blog.app.auth.service.impl.UserAuthService;
import com.blog.app.main.dto.request.UserProfileUpdateRequest;
import com.blog.app.main.dto.response.UserProfileResponse;
import com.blog.app.main.entity.UserProfile;
import com.blog.app.main.repository.UserProfileRepository;
import com.blog.app.main.service.UserProfileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAuthService userAuthService;

	@Override
	public UserProfileResponse getUserProfile(HttpServletRequest servletRequest) {
		User userInSession = userAuthService.getUserInSession(servletRequest);
		return createUserProfileResponse(userInSession);
	}

	@Override
	public UserProfileResponse updateUserProfile(HttpServletRequest servletRequest,
			@Valid UserProfileUpdateRequest profileUpdateRequest) {
		User userInSession = userAuthService.getUserInSession(servletRequest);
		userInSession.setEmail(profileUpdateRequest.getEmail());
		userInSession.setUsername(profileUpdateRequest.getUserName());
		User user = userRepository.save(userInSession);

		UserProfile userProfile = userProfileRepository.findProfileByUserId(userInSession.getId()).get();
		userProfile.setFullName(profileUpdateRequest.getFullName());
		userProfile.setPhoneNumber(profileUpdateRequest.getPhoneNumber());
		userProfileRepository.save(userProfile);

		return createUserProfileResponse(user);
	}

	private UserProfileResponse createUserProfileResponse(User user) {
		UserProfile profile = userProfileRepository.findProfileByUserId(user.getId()).get();

		return UserProfileResponse.builder().userId(user.getId()).userName(user.getUsername())
				.fullName(profile.getFullName()).email(user.getEmail()).joinedOn(profile.getJoinedOn().getTime())
				.lastUpdatedOn(profile.getLastUpdatedOn().getTime()).lastSignIn(profile.getLastSignIn().getTime())
				.build();
	}
}
