package com.blog.app.main.dto.request;

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
public class UserProfileCreationRequest {

	@NotNull(message = "Full name should not be null")
	@Size(min = 1, message = "Full name is required")
	@Size(max = 50, message = "Full name should be a maximum of 50 characters long")
	private String fullName;
}
