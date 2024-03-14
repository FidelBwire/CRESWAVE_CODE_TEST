package com.blog.app.main.service;

import com.blog.app.main.dto.request.BlogPostRequest;
import com.blog.app.main.dto.response.BlogPostResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

public interface BlogPostService {

	BlogPostResponse createBlogPost(HttpServletRequest servletRequest, BlogPostRequest blogPostRequest);

	BlogPostResponse updateBlogPost(HttpServletRequest servletRequest, @Valid BlogPostRequest blogPostRequest);

}
