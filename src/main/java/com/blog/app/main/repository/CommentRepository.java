package com.blog.app.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.app.main.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "SELECT * FROM comments WHERE blog_post_id = :blogId", nativeQuery = true)
	Page<Comment> findBlogComments(@Param(value = "blogId") String blogId, PageRequest pageRequest);

}
