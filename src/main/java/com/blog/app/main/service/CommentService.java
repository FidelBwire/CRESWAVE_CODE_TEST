package com.blog.app.main.service;

import org.springframework.data.domain.Page;

import com.blog.app.main.dto.request.CommentRequest;
import com.blog.app.main.dto.response.CommentResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface CommentService {

	Page<CommentResponse> getBlogComments(int page, int size);

	CommentResponse postComment(HttpServletRequest servletRequest, String blogId, @Valid CommentRequest commentRequest);

}
