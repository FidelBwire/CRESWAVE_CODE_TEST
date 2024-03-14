package com.blog.app.main.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileUpdateRequest {

	@NotNull(message = "Full name should not be null")
	@Size(min = 1, message = "Full name is a required field")
	@Size(max = 50, message = "Full name should be a maximum of 50 characters long")
	private String fullName;

	@NotNull(message = "Email should not be null")
	@Email(message = "Wrong email address format")
	@Size(min = 1, message = "Email is a required field")
	@Size(max = 100, message = "Email should be a maximum of 100 characters long")
	private String email;

	@NotNull(message = "Username should not be null")
	@Size(min = 6, max = 20, message = "Username should be 6 to 20 characters long")
	private String userName;

	@Size(min = 10, max = 12, message = "Invalid phone number length")
	private String phoneNumber;
}
