package com.blog.app.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.auth.entity.User;
import com.blog.app.main.entity.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, String> {

	boolean existsByAuthorAndId(User userInSession, String blogId);

}
