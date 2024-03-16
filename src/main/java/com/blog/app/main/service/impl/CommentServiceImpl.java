package com.blog.app.main.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.blog.app.auth.entity.User;
import com.blog.app.auth.exceptions.handlers.ResourceNotFoundException;
import com.blog.app.auth.service.impl.UserAuthService;
import com.blog.app.main.dto.request.CommentRequest;
import com.blog.app.main.dto.response.CommentResponse;
import com.blog.app.main.entity.BlogPost;
import com.blog.app.main.entity.Comment;
import com.blog.app.main.repository.BlogPostRepository;
import com.blog.app.main.repository.CommentRepository;
import com.blog.app.main.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserAuthService authService;

	@Autowired
	private BlogPostRepository blogPostRepository;

	@Override
	public Page<CommentResponse> getBlogComments(int page, int size) {
		Order order = Order.asc("createdOn");
		Sort sort = Sort.by(order);
		PageRequest pageRequest = PageRequest.of(page, size, sort);

		Page<Comment> comments = commentRepository.findAll(pageRequest);
		List<CommentResponse> commentsResponse = comments.stream()
				.map(comment -> CommentResponse.builder().id(comment.getId()).build()).toList();

		return new PageImpl<>(commentsResponse, pageRequest, comments.getTotalElements());
	}

	@Override
	public CommentResponse postComment(HttpServletRequest servletRequest, String blogId,
			@Valid CommentRequest commentRequest) {
		User user = authService.getUserInSession(servletRequest);
		BlogPost blogPost = getBlogPost(blogId);

		Comment comment = Comment.builder().user(user).content(commentRequest.getComment()).blogPost(blogPost).build();
		Comment newComment = commentRepository.save(comment);

		return createCommentResponse(newComment);
	}

	private BlogPost getBlogPost(String blogId) {
		return blogPostRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid blog Id: " + blogId));
	}

	private CommentResponse createCommentResponse(Comment comment) {
		CommentResponse response = CommentResponse.builder().id(comment.getId()).author(comment.getUser().getUsername())
				.content(comment.getContent()).createdOn(comment.getCreatedOn().getTime())
				.updatedOn(comment.getLastUpdatedOn().getTime()).build();
		response.setSubComments(
				comment.getComments() != null ? comment.getComments().stream().map(this::createCommentResponse).toList()
						: new ArrayList<>());
		return response;
	}
}
