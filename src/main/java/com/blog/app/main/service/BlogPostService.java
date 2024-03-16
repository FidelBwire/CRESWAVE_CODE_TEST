package com.blog.app.main.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.blog.app.main.dto.request.BlogPostRequest;
import com.blog.app.main.dto.response.BlogPostResponse;
import com.blog.app.main.dto.response.BlogPostSummaryResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface BlogPostService {

	BlogPostResponse createBlogPost(HttpServletRequest servletRequest, BlogPostRequest blogPostRequest);

	BlogPostResponse updateBlogPost(HttpServletRequest servletRequest, String blogId,
			@Valid BlogPostRequest blogPostRequest);

	String deleteBlogPost(HttpServletRequest servletRequest, String blogId);

	Page<BlogPostSummaryResponse> getBlogPosts(int page, int size, String orderBy, String direction,
			Optional<String> title, Optional<String> content);

	BlogPostResponse getBlogPost(String blogId);

}
