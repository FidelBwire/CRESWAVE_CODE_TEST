package com.blog.app.main.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CommentRequest {

	@NotNull(message = "Full name should not be null")
	@Size(min = 1, message = "Comment must not be empty")
	@Size(max = 200, message = "Comment should be a maximum of 200 characters")
	private String comment;
}
