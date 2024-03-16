package com.blog.app.main.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CommentResponse {

	private Long id;
	private String author;
	private String content;
	private Long createdOn;
	private Long updatedOn;
	private List<CommentResponse> subComments;
}
