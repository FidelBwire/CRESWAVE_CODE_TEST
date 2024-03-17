package com.blog.app.main.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.blog.app.auth.entity.User;
import com.blog.app.auth.service.impl.UserAuthService;
import com.blog.app.main.dto.request.CommentRequest;
import com.blog.app.main.dto.response.CommentResponse;
import com.blog.app.main.entity.BlogPost;
import com.blog.app.main.entity.Comment;
import com.blog.app.main.repository.BlogPostRepository;
import com.blog.app.main.repository.CommentRepository;

import jakarta.servlet.http.HttpServletRequest;

class CommentServiceImplTest {

	@Mock
	private CommentRepository commentRepository;

	@Mock
	private UserAuthService authService;

	@Mock
	private BlogPostRepository blogPostRepository;

	@InjectMocks
	private CommentServiceImpl commentService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testPostComment() {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		String blogId = "1";
		CommentRequest commentRequest = getTestCommentRequest();

		User user = new User();

		BlogPost blogPost = new BlogPost();
		blogPost.setId(blogId);

		when(authService.getUserInSession(servletRequest)).thenReturn(user);
		when(blogPostRepository.findById(blogId)).thenReturn(java.util.Optional.of(blogPost));

		Comment savedComment = getTestComment();

		when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

		CommentResponse result = commentService.postComment(servletRequest, blogId, commentRequest);

		assertEquals(1L, result.getId());
		assertEquals("Original comment", result.getContent());

	}

	@Test
	void testCommentOnComment() {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		Long commentId = 1L;
		CommentRequest commentRequest = new CommentRequest();
		commentRequest.setComment("Reply to the comment");

		User user = new User();

		Comment parentComment = getTestComment();

		when(authService.getUserInSession(servletRequest)).thenReturn(user);
		when(commentRepository.findById(commentId)).thenReturn(java.util.Optional.of(parentComment));

		Comment savedComment = new Comment();
		savedComment.setId(2L);
		savedComment.setUser(user);
		savedComment.setContent("Reply to the comment");
		savedComment.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		savedComment.setLastUpdatedOn(new Timestamp(System.currentTimeMillis()));
		savedComment.setBlogPost(new BlogPost());

		when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

		CommentResponse result = commentService.commentOnComment(servletRequest, commentId, commentRequest);

		assertEquals(2L, result.getId());
		assertEquals("Reply to the comment", result.getContent());

	}

	@Test
	void testUpdateComment() {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		CommentRequest commentRequest = getTestCommentRequest();

		User user = getTestUser();

		Comment comment = getTestComment();
		comment.setUser(user);
		Long commentId = comment.getId();

		when(authService.getUserInSession(servletRequest)).thenReturn(user);
		when(commentRepository.findById(commentId)).thenReturn(java.util.Optional.of(comment));
		when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

		CommentResponse result = commentService.updateComment(servletRequest, commentId, commentRequest);

		assertEquals(commentId, result.getId());
		assertEquals("Updated comment", result.getContent());

	}

	@Test
	void testDeleteComment() {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		User user = getTestAdminUser();

		Comment comment = getTestComment();
		comment.setUser(user);

		when(authService.getUserInSession(servletRequest)).thenReturn(user);
		when(commentRepository.findById(comment.getId())).thenReturn(java.util.Optional.of(comment));

		String result = commentService.deleteComment(servletRequest, comment.getId());

		assertEquals("Deleted successfully", result);
	}

	private User getTestUser() {
		User user = new User();
		String userId = UUID.randomUUID().toString();
		user.setId(userId);
		user.setUsername("john_doe");
		user.setEmail("johndoe@gmail.com");
		user.setRole("ROLE_USER");
		user.setAuthority("USER");

		return user;
	}

	private User getTestAdminUser() {
		User user = new User();
		String userId = UUID.randomUUID().toString();
		user.setId(userId);
		user.setUsername("admin_user");
		user.setEmail("admin_user@gmail.com");
		user.setRole("ROLE_ADMIN");
		user.setAuthority("ADMIN");

		return user;
	}

	private Comment getTestComment() {
		Comment comment = new Comment();
		comment.setId((long) 1);
		comment.setUser(getTestUser());
		comment.setContent("Original comment");
		comment.setComments(new ArrayList<>());
		comment.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		comment.setLastUpdatedOn(new Timestamp(System.currentTimeMillis()));

		return comment;
	}

	private CommentRequest getTestCommentRequest() {
		CommentRequest commentRequest = new CommentRequest();
		commentRequest.setComment("Updated comment");

		return commentRequest;
	}
}
