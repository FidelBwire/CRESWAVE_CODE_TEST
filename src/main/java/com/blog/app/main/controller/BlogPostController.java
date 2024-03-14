package com.blog.app.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.main.dto.request.BlogPostRequest;
import com.blog.app.main.dto.response.BlogPostResponse;
import com.blog.app.main.service.BlogPostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blogs")
public class BlogPostController {

	@Autowired
	private BlogPostService blogPostService;

	@PostMapping()
	public ResponseEntity<BlogPostResponse> createBlogPost(HttpServletRequest servletRequest,
			@Valid @RequestBody BlogPostRequest blogPostRequest) {
		BlogPostResponse postResponse = blogPostService.createBlogPost(servletRequest, blogPostRequest);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<BlogPostResponse> updateBlogPost(HttpServletRequest servletRequest,
			@Valid @RequestBody BlogPostRequest blogPostRequest) {
		BlogPostResponse postResponse = blogPostService.updateBlogPost(servletRequest, blogPostRequest);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

}
