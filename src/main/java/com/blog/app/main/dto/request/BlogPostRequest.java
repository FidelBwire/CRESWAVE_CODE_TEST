package com.blog.app.main.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogPostRequest {

	@NotNull(message = "Title should not be null")
	@Size(min = 1, message = "Title is a required field")
	@Size(max = 250, message = "Title should be a maximum of 250 characters long")
	private String title;

	@NotNull(message = "Content should not be null")
	@Size(min = 1, message = "Content is a required field")
	@Size(max = 250, message = "Content should be a maximum of 250 characters long")
	private String content;
}
