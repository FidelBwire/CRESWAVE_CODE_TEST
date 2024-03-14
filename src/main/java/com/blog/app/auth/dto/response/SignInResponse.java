package com.blog.app.auth.dto.response;

import java.util.List;

import com.blog.app.main.dto.response.UserProfileResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignInResponse {

	private String authToken;
	private List<String> roles;
	private String authority;
	private UserProfileResponse userProfile;
}
