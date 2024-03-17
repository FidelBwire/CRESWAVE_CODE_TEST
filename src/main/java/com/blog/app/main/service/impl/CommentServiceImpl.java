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
import com.blog.app.auth.exceptions.handlers.ForbiddenActionException;
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
	public Page<CommentResponse> getBlogComments(int page, int size, String blogId) {
		Order order = Order.asc("created_on");
		Sort sort = Sort.by(order);
		PageRequest pageRequest = PageRequest.of(page - 1, size, sort);

		if (!blogPostRepository.existsById(blogId)) {
			throw new ResourceNotFoundException("Invalid blog Id: " + blogId);
		}

		Page<Comment> comments = commentRepository.findBlogComments(blogId, pageRequest);
		List<CommentResponse> commentsResponse = comments.getContent().stream().map(this::createCommentResponse)
				.toList();

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

	@Override
	public CommentResponse commentOnComment(HttpServletRequest servletRequest, Long commentId,
			@Valid CommentRequest commentRequest) {
		User user = authService.getUserInSession(servletRequest);

		Comment parentComment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid parent comment Id: " + commentId));

		Comment comment = Comment.builder().user(user).content(commentRequest.getComment()).build();
		Comment newComment = commentRepository.save(comment);

		parentComment.getComments().add(newComment);
		commentRepository.save(parentComment);

		return createCommentResponse(newComment);
	}

	@Override
	public CommentResponse updateComment(HttpServletRequest servletRequest, Long commentId,
			@Valid CommentRequest commentRequest) {
		User user = authService.getUserInSession(servletRequest);
		Comment comment = getUserComment(user, commentId);
		comment.setContent(commentRequest.getComment());
		comment = commentRepository.save(comment);

		return createCommentResponse(comment);
	}

	@Override
	public String deleteComment(HttpServletRequest servletRequest, Long commentId) {
		User user = authService.getUserInSession(servletRequest);
		Comment comment = getUserComment(user, commentId);

		commentRepository.delete(comment);
		return "Deleted successfully";
	}

	private BlogPost getBlogPost(String blogId) {
		return blogPostRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid blog Id: " + blogId));
	}

	private Comment getUserComment(User user, Long commentId) {
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid comment Id: " + commentId));

		if (!comment.getUser().getId().equals(user.getId())) {
			throw new ForbiddenActionException("Action forbidden. Acess denied for the comment " + commentId);
		}

		return comment;
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
