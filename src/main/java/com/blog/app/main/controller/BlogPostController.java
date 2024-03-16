package com.blog.app.main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.main.dto.request.BlogPostRequest;
import com.blog.app.main.dto.response.BlogPostResponse;
import com.blog.app.main.dto.response.BlogPostSummaryResponse;
import com.blog.app.main.service.BlogPostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blogs")
public class BlogPostController {

	@Autowired
	private BlogPostService blogPostService;

	@GetMapping
	public ResponseEntity<Page<BlogPostSummaryResponse>> getBlogPosts(
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "15") int size,
			@RequestParam(name = "orderBy", required = false, defaultValue = "createdOn") String orderBy,
			@RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
			@RequestParam(name = "title", required = false) Optional<String> title,
			@RequestParam(name = "content", required = false) Optional<String> content) {
		Page<BlogPostSummaryResponse> blogs = blogPostService.getBlogPosts(page, size, orderBy, direction, title,
				content);
		return new ResponseEntity<>(blogs, HttpStatus.OK);
	}

	@GetMapping("/{blogId}")
	public ResponseEntity<BlogPostResponse> getBlogPost(@PathVariable(required = true) String blogId) {
		BlogPostResponse postResponse = blogPostService.getBlogPost(blogId);
		return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
	}

	@PostMapping
	public ResponseEntity<BlogPostResponse> createBlogPost(HttpServletRequest servletRequest,
			@Valid @RequestBody BlogPostRequest blogPostRequest) {
		BlogPostResponse postResponse = blogPostService.createBlogPost(servletRequest, blogPostRequest);
		return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{blogId}")
	public ResponseEntity<BlogPostResponse> updateBlogPost(HttpServletRequest servletRequest,
			@Valid @RequestBody BlogPostRequest blogPostRequest, @PathVariable String blogId) {
		BlogPostResponse postResponse = blogPostService.updateBlogPost(servletRequest, blogId, blogPostRequest);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{blogId}")
	public ResponseEntity<String> deletePost(HttpServletRequest servletRequest, @PathVariable String blogId) {
		String response = blogPostService.deleteBlogPost(servletRequest, blogId);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

}
