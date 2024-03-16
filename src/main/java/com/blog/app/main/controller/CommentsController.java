package com.blog.app.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.main.dto.request.CommentRequest;
import com.blog.app.main.dto.response.CommentResponse;
import com.blog.app.main.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/blogs/{blogId}/comments")
public class CommentsController {

	@Autowired
	private CommentService commentService;

	@GetMapping
	public ResponseEntity<Page<CommentResponse>> getBlogComments(
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "15") int size) {
		Page<CommentResponse> comments = commentService.getBlogComments(page - 1, size);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CommentResponse> postComment(HttpServletRequest servletRequest,
			@PathVariable(required = true, name = "blogId") String blogId,
			@Valid @RequestBody CommentRequest commentRequest) {
		CommentResponse response = commentService.postComment(servletRequest, blogId, commentRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
