package com.blog.app.main.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.blog.app.auth.entity.User;
import com.blog.app.auth.service.impl.UserAuthService;
import com.blog.app.main.dto.request.BlogPostRequest;
import com.blog.app.main.dto.response.BlogPostResponse;
import com.blog.app.main.dto.response.BlogPostSummaryResponse;
import com.blog.app.main.entity.BlogPost;
import com.blog.app.main.entity.BlogPostSummary;
import com.blog.app.main.repository.BlogPostRepository;
import com.blog.app.main.repository.BlogPostSummaryRepository;
import com.blog.app.main.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;

class BlogPostServiceImplTest {

	@Mock
	private UserAuthService userAuthService;

	@Mock
	private BlogPostRepository blogPostRepository;

	@Mock
	private BlogPostSummaryRepository blogPostSummaryRepository;

	@Mock
	private CommentService commentService;

	@InjectMocks
	private BlogPostServiceImpl blogPostService;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetBlogPosts() {
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("created_on").descending());
		BlogPostSummary blogPostSummary = getTestBlogPostSummary();

		Page<BlogPostSummary> page = new PageImpl<>(Collections.singletonList(blogPostSummary));
		when(blogPostSummaryRepository.findAll(pageRequest)).thenReturn(page);

		Page<BlogPostSummaryResponse> result = blogPostService.getBlogPosts(1, 10, "created_on", "desc",
				Optional.empty(), Optional.empty());

		assertEquals(1, result.getContent().size());
		assertEquals("1", result.getContent().get(0).getId());
	}

	@Test
	void testGetBlogPost() {
		BlogPost blogPost = getTestBlogPost();
		when(blogPostRepository.findById("1")).thenReturn(Optional.of(blogPost));

		BlogPostResponse result = blogPostService.getBlogPost("1");

		assertEquals("1", result.getId());
		assertEquals("Test Title", result.getTitle());
	}

	@Test
	void testCreateBlogPost() {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		BlogPostRequest blogPostRequest = getTestBlogPostRequest();
		User user = getTestUser();

		when(userAuthService.getUserInSession(servletRequest)).thenReturn(user);
		when(blogPostRepository.save(any(BlogPost.class))).thenReturn(getTestUpdatedBlogPost());

		BlogPostResponse result = blogPostService.createBlogPost(servletRequest, blogPostRequest);

		assertEquals("1", result.getId());
		assertEquals(user.getId(), result.getAuthorId());
		assertEquals("Test Title Updated", result.getTitle());
		assertEquals("Test Content Updated", result.getContent());
	}

	@Test
	void testUpdateBlogPost() {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		BlogPostRequest blogPostRequest = new BlogPostRequest();
		blogPostRequest.setTitle("Updated Title");
		blogPostRequest.setContent("Updated Content");

		User user = getTestUser();
		when(userAuthService.getUserInSession(servletRequest)).thenReturn(user);

		BlogPost existingBlogPost = getTestBlogPost();

		when(blogPostRepository.existsById("1")).thenReturn(true);
		when(blogPostRepository.findOne(any())).thenReturn(Optional.of(existingBlogPost));
		when(blogPostRepository.save(any(BlogPost.class))).thenAnswer(invocation -> invocation.getArgument(0));

		BlogPostResponse result = blogPostService.updateBlogPost(servletRequest, "1", blogPostRequest);

		assertEquals("1", result.getId());
		assertEquals("Updated Title", result.getTitle());
	}

	@Test
	void testDeleteBlogPost() {
		HttpServletRequest servletRequest = mock(HttpServletRequest.class);
		User user = getTestUser();
		when(userAuthService.getUserInSession(servletRequest)).thenReturn(user);
		when(blogPostRepository.existsById("1")).thenReturn(true);

		String result = blogPostService.deleteBlogPost(servletRequest, "1");

		assertEquals("Blog deleted successfully", result);
	}

	private BlogPostSummary getTestBlogPostSummary() {
		BlogPostSummary blogPostSummary = new BlogPostSummary();
		blogPostSummary.setId("1");
		blogPostSummary.setTitle("Test Title");
		blogPostSummary.setAuthor(getTestUser());
		blogPostSummary.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		blogPostSummary.setLastUpdatedOn(new Timestamp(System.currentTimeMillis()));

		return blogPostSummary;
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

	private BlogPost getTestBlogPost() {
		BlogPost blogPost = new BlogPost();
		blogPost.setId("1");
		blogPost.setTitle("Test Title");
		blogPost.setContent("Test Content");
		blogPost.setAuthor(getTestUser());
		blogPost.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		blogPost.setLastUpdatedOn(new Timestamp(System.currentTimeMillis()));

		return blogPost;
	}

	private BlogPostRequest getTestBlogPostRequest() {
		BlogPostRequest blogPostRequest = new BlogPostRequest();
		blogPostRequest.setTitle("Test Title Updated");
		blogPostRequest.setContent("Test Content Updated");

		return blogPostRequest;
	}

	private BlogPost getTestUpdatedBlogPost() {
		BlogPost blogPost = new BlogPost();
		blogPost.setId("1");
		blogPost.setTitle("Test Title Updated");
		blogPost.setContent("Test Content Updated");
		blogPost.setAuthor(getTestUser());
		blogPost.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		blogPost.setLastUpdatedOn(new Timestamp(System.currentTimeMillis()));

		return blogPost;
	}
}
