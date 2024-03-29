package com.blog.app.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SignUpRequest {

	@NotNull(message = "Username should not be null")
	@Size(min = 6, max = 20, message = "Username should be 6 to 20 characters long")
	private String userName;

	@NotNull(message = "Passord should not be null")
	@Size(min = 6, max = 50, message = "Passord should be 6 to 50 characters long")
	private String password;

	@NotNull(message = "Full name should not be null")
	@Size(min = 1, message = "Full name is required")
	@Size(max = 50, message = "Full name should be a maximum of 50 characters long")
	private String fullName;

	@Size(min = 10, max = 12, message = "Invalid phone number length")
	private String phoneNumber;

	@NotNull(message = "Email should not be null")
	@Email(message = "Wrong email address format")
	@Size(max = 100, message = "Email should be a maximum of 100 characters long")
	private String email;
}
