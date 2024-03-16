package com.blog.app.main.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.blog.app.auth.entity.User;
import com.blog.app.auth.exceptions.handlers.ForbiddenAccessException;
import com.blog.app.auth.exceptions.handlers.ResourceNotFoundException;
import com.blog.app.auth.service.impl.UserAuthService;
import com.blog.app.main.dto.request.BlogPostRequest;
import com.blog.app.main.dto.response.BlogPostResponse;
import com.blog.app.main.dto.response.BlogPostSummaryResponse;
import com.blog.app.main.dto.response.CommentResponse;
import com.blog.app.main.entity.BlogPost;
import com.blog.app.main.entity.BlogPostSummary;
import com.blog.app.main.repository.BlogPostRepository;
import com.blog.app.main.repository.BlogPostSummaryRepository;
import com.blog.app.main.service.BlogPostService;
import com.blog.app.main.service.CommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Service
public class BlogPostServiceImpl implements BlogPostService {

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private BlogPostRepository blogPostRepository;

	@Autowired
	private BlogPostSummaryRepository blogPostSummaryRepository;

	@Autowired
	private CommentService commentService;

	@Override
	public Page<BlogPostSummaryResponse> getBlogPosts(int page, int size, String orderBy, String direction,
			Optional<String> title, Optional<String> content) {
		Order order = direction.equalsIgnoreCase("asc") ? Order.asc(orderBy) : Order.desc(orderBy);
		Sort sort = Sort.by(order);
		PageRequest pageRequest = PageRequest.of(page - 1, size, sort);

		Page<BlogPostSummary> blogs = blogPostSummaryRepository.findAll(pageRequest);
		List<BlogPostSummaryResponse> blogResponse = blogs.getContent().stream()
				.map(blog -> BlogPostSummaryResponse.builder().id(blog.getId()).authorId(blog.getAuthor().getId())
						.author(blog.getAuthor().getUsername()).title(blog.getTitle())
						.createdOn(blog.getCreatedOn().getTime()).lastUpdatedOn(blog.getLastUpdatedOn().getTime())
						.build())
				.toList();
		return new PageImpl<>(blogResponse, pageRequest, blogs.getTotalElements());
	}

	@Override
	public BlogPostResponse getBlogPost(String blogId) {
		BlogPost blog = blogPostRepository.findById(blogId)
				.orElseThrow(() -> new ResourceNotFoundException("Blog post not found: " + blogId));

		Page<CommentResponse> comments = commentService.getBlogComments(1, 10, blogId);

		return BlogPostResponse.builder().id(blog.getId()).authorId(blog.getAuthor().getId())
				.author(blog.getAuthor().getUsername()).title(blog.getTitle()).content(blog.getContent())
				.comments(comments).createdOn(blog.getCreatedOn().getTime())
				.lastUpdatedOn(blog.getLastUpdatedOn().getTime()).build();

	}

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
	public BlogPostResponse updateBlogPost(HttpServletRequest servletRequest, String blogId,
			@Valid BlogPostRequest blogRequest) {
		User userInSession = userAuthService.getUserInSession(servletRequest);

		if (!blogPostRepository.existsById(blogId)) {
			throw new ResourceNotFoundException("Blog post not found");
		}

		Example<BlogPost> blogPostExample = Example.of(BlogPost.builder().author(userInSession).id(blogId).build());

		BlogPost blog = blogPostRepository.findOne(blogPostExample)
				.orElseThrow(() -> new ForbiddenAccessException("Access denied! You can only update your blogs"));

		blog.setTitle(blogRequest.getTitle());
		blog.setContent(blogRequest.getContent());
		BlogPost updatedBlog = blogPostRepository.save(blog);

		BlogPostResponse blogPostResponse = createBlogPostResponse(updatedBlog, userInSession);
		return blogPostResponse;
	}

//	@Override
//	public String deleteBlogPost(HttpServletRequest servletRequest, String blogId) {
//		User userInSession = userAuthService.getUserInSession(servletRequest);
//
//		if (!blogPostRepository.existsById(blogId)) {
//			throw new ResourceNotFoundException("Blog post not found");
//		}
//
//		if (blogPostRepository.existsByAuthorAndId(userInSession, blogId)) {
//			blogPostRepository.deleteById(blogId);
//			return "Blog deleted successfully";
//		} else {
//			throw new ForbiddenActionException("Access denied! You can only delete your blog posts");
//		}
//	}

	@Override
	public String deleteBlogPost(HttpServletRequest servletRequest, String blogId) {
		if (!blogPostRepository.existsById(blogId)) {
			throw new ResourceNotFoundException("Blog post not found");
		}

		blogPostRepository.deleteById(blogId);
		return "Blog deleted successfully";
	}

	private BlogPostResponse createBlogPostResponse(BlogPost blog, User user) {
		return BlogPostResponse.builder().id(blog.getId()).authorId(user.getId()).author(user.getUsername())
				.title(blog.getTitle()).content(blog.getContent()).createdOn(blog.getCreatedOn().getTime())
				.lastUpdatedOn(blog.getLastUpdatedOn().getTime()).build();
	}

}
