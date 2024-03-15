package com.blog.app.main.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.blog.app.main.dto.response.CommentResponse;
import com.blog.app.main.entity.Comment;
import com.blog.app.main.repository.CommentRepository;
import com.blog.app.main.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

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

}
