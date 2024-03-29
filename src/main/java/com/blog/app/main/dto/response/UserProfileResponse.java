package com.blog.app.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileResponse {

	private String userId;
	private String userName;
	private String fullName;
	private String email;
	private String phoneNumber;
	private Long joinedOn;
	private Long lastUpdatedOn;
	private Long lastSignIn;
}
