package com.blog.app.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.auth.entity.User;
import com.blog.app.auth.service.impl.UserAuthService;
import com.blog.app.main.dto.request.BlogPostRequest;
import com.blog.app.main.dto.response.BlogPostResponse;
import com.blog.app.main.entity.BlogPost;
import com.blog.app.main.repository.BlogPostRepository;
import com.blog.app.main.service.BlogPostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Service
public class BlogPostServiceImpl implements BlogPostService {

	@Autowired
	private BlogPostRepository blogPostRepository;

	@Autowired
	private UserAuthService userAuthService;

	@Override
	public BlogPostResponse createBlogPost(HttpServletRequest servletRequest, BlogPostRequest blogPostRequest) {
		User userInSession = userAuthService.getUserInSession(servletRequest);

		BlogPost blogPost = BlogPost.builder().author(userInSession).title(blogPostRequest.getTitle())
				.content(blogPostRequest.getContent()).build();
		BlogPost newBlogPost = blogPostRepository.save(blogPost);
		BlogPostResponse blogPostResponse = createBlogPostResponse(newBlogPost, userInSession);
		return blogPostResponse;
	}

	@Override
	public BlogPostResponse updateBlogPost(HttpServletRequest servletRequest, @Valid BlogPostRequest blogPostRequest) {

		return null;
	}

	private BlogPostResponse createBlogPostResponse(BlogPost blog, User user) {
		return BlogPostResponse.builder().id(blog.getId()).authorId(user.getId()).author(user.getUsername())
				.title(blog.getTitle()).content(blog.getContent()).createdOn(blog.getCreatedOn().getTime())
				.lastUpdatedOn(blog.getLastUpdatedOn().getTime()).build();
	}

}
