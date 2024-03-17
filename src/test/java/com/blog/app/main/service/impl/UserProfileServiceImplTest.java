package com.blog.app.main.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.blog.app.auth.entity.User;
import com.blog.app.auth.repository.UserRepository;
import com.blog.app.auth.service.impl.UserAuthService;
import com.blog.app.main.dto.request.UserProfileUpdateRequest;
import com.blog.app.main.dto.response.UserProfileResponse;
import com.blog.app.main.entity.UserProfile;
import com.blog.app.main.repository.UserProfileRepository;

import jakarta.servlet.http.HttpServletRequest;

class UserProfileServiceImplTest {

	@Mock
	private UserProfileRepository userProfileRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserAuthService userAuthService;

	@InjectMocks
	private UserProfileServiceImpl userProfileService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetUserProfile() {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		User user = getTestUser();
		UserProfile userProfile = getTestUserProfile();

		when(userAuthService.getUserInSession(servletRequest)).thenReturn(user);
		when(userProfileRepository.findProfileByUserId(user.getId())).thenReturn(Optional.of(userProfile));

		UserProfileResponse userProfileResponse = userProfileService.getUserProfile(servletRequest);

		assertNotNull(userProfileResponse);
		assertEquals(user.getId(), userProfileResponse.getUserId());
		assertEquals(user.getUsername(), userProfileResponse.getUserName());
		assertEquals(user.getEmail(), userProfileResponse.getEmail());
		assertEquals(userProfile.getFullName(), userProfileResponse.getFullName());
		assertEquals(userProfile.getPhoneNumber(), userProfileResponse.getPhoneNumber());
	}

	@Test
	void testUpdateUserProfile() {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		UserProfileUpdateRequest profileUpdateRequest = getTestUserProfileUpdateRequest();
		User user = getTestUser();
		UserProfile userProfile = getTestUserProfile();

		when(userAuthService.getUserInSession(servletRequest)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		when(userProfileRepository.findProfileByUserId(user.getId())).thenReturn(Optional.of(userProfile));
		when(userProfileRepository.save(userProfile)).thenReturn(userProfile);

		UserProfileResponse userProfileResponse = userProfileService.updateUserProfile(servletRequest,
				profileUpdateRequest);

		assertNotNull(userProfileResponse);
		assertEquals(user.getId(), userProfileResponse.getUserId());
		assertEquals(profileUpdateRequest.getUserName(), userProfileResponse.getUserName());
		assertEquals(profileUpdateRequest.getEmail(), userProfileResponse.getEmail());
		assertEquals(profileUpdateRequest.getFullName(), userProfileResponse.getFullName());
		assertEquals(profileUpdateRequest.getPhoneNumber(), userProfileResponse.getPhoneNumber());

	}

	private User getTestUser() {
		User user = new User();
		String userId = UUID.randomUUID().toString();
		user.setId(userId);
		user.setUsername("john_doe");
		user.setEmail("johndoe@gmail.com");
		user.setRole("ROLE_USER");
		user.setAuthority("USER");

		return user;
	}

	private UserProfileUpdateRequest getTestUserProfileUpdateRequest() {
		UserProfileUpdateRequest profileUpdateRequest = new UserProfileUpdateRequest();
		profileUpdateRequest.setEmail("johndoeupdated@gmail.com");
		profileUpdateRequest.setUserName("john_doe_updated");
		profileUpdateRequest.setFullName("John Doe_Updated");
		profileUpdateRequest.setPhoneNumber("0712345678");

		return profileUpdateRequest;
	}

	private UserProfile getTestUserProfile() {
		UserProfile userProfile = new UserProfile();
		userProfile.setFullName("John Doe");
		userProfile.setPhoneNumber("0712345678");
		userProfile.setJoinedOn(new Timestamp(System.currentTimeMillis()));
		userProfile.setLastUpdatedOn(new Timestamp(System.currentTimeMillis()));
		userProfile.setLastSignIn(new Timestamp(System.currentTimeMillis()));
		userProfile.setUser(getTestUser());

		return userProfile;
	}
}
