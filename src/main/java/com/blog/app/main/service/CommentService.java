package com.blog.app.main.service;

import org.springframework.data.domain.Page;

import com.blog.app.main.dto.response.CommentResponse;

public interface CommentService {

	Page<CommentResponse> getBlogComments(int page, int size);

}
