package com.blog.app.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.main.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
