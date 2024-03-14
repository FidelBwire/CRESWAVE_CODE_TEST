package com.blog.app.main.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BlogPostResponse {

	private String id;
	private String authorId;
	private String author;
	private String title;
	private String content;
	private Long createdOn;
	private Long lastUpdatedOn;
	private List<Page<CommentResponse>> comments;
}
