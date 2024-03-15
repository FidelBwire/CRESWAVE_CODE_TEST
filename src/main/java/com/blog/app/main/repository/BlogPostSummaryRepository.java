package com.blog.app.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.main.entity.BlogPostSummary;

public interface BlogPostSummaryRepository extends JpaRepository<BlogPostSummary, String> {

//	Page<BlogPostResponse> getBlogPosts(PageRequest pageRequest);

}
