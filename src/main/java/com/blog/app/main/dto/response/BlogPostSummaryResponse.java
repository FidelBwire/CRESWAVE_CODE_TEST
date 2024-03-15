package com.blog.app.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BlogPostSummaryResponse {

	private String id;
	private String authorId;
	private String author;
	private String title;
	private Long createdOn;
	private Long lastUpdatedOn;
}
