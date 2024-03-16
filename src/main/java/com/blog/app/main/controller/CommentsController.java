package com.blog.app.main.controller;

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

import com.blog.app.main.dto.request.CommentRequest;
import com.blog.app.main.dto.response.CommentResponse;
import com.blog.app.main.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping
public class CommentsController {

	@Autowired
	private CommentService commentService;

	@GetMapping("/blogs/{blogId}/comments")
	public ResponseEntity<Page<CommentResponse>> getBlogComments(
			@RequestParam(name = "page", required = false, defaultValue = "1") int page,
			@RequestParam(name = "size", required = false, defaultValue = "15") int size,
			@PathVariable(required = true, name = "blogId") String blogId) {
		Page<CommentResponse> comments = commentService.getBlogComments(page, size, blogId);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@PostMapping("/blogs/{blogId}/comments")
	public ResponseEntity<CommentResponse> postComment(HttpServletRequest servletRequest,
			@PathVariable(required = true, name = "blogId") String blogId,
			@Valid @RequestBody CommentRequest commentRequest) {
		CommentResponse response = commentService.postComment(servletRequest, blogId, commentRequest);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/blogs/{blogId}/comments/{commentId}")
	public ResponseEntity<CommentResponse> commentOnComment(HttpServletRequest servletRequest,
			@PathVariable(required = true, name = "commentId") Long commentId,
			@Valid @RequestBody CommentRequest commentRequest) {
		CommentResponse response = commentService.commentOnComment(servletRequest, commentId, commentRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/comments/{commentId}")
	public ResponseEntity<CommentResponse> updateComment(HttpServletRequest servletRequest,
			@PathVariable(required = true, name = "commentId") Long commentId,
			@Valid @RequestBody CommentRequest commentRequest) {
		CommentResponse response = commentService.updateComment(servletRequest, commentId, commentRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<String> deleteComment(HttpServletRequest servletRequest,
			@PathVariable(required = true, name = "commentId") Long commentId) {
		String response = commentService.deleteComment(servletRequest, commentId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
