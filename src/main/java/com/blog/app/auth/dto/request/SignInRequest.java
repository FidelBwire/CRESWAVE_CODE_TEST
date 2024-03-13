package com.blog.app.auth.dto.request;

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
public class SignInRequest {

	@NotNull(message = "Passord should not be null")
	@Size(min = 6, max = 50, message = "Passord should be 6 to 50 characters long")
	private String password;

	@NotNull(message = "Email should not be null")
	@Email(message = "Wrong email address format")
	@Size(max = 100, message = "Email should be a maximum of 100 characters long")
	private String email;
}
