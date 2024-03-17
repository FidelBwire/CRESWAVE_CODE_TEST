package com.blog.app.main.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.blog.app.auth.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "BlogPosts")
@Entity
public class BlogPost {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private User author;

	@Column(nullable = false, length = 250, columnDefinition = "VARCHAR(250) NOT NULL")
	private String title;

	@Column(nullable = false, length = 3000, columnDefinition = "VARCHAR(3000) NOT NULL")
	private String content;

	@CreationTimestamp
	@Column(updatable = false, nullable = false)
	private Timestamp createdOn;

	@UpdateTimestamp
	private Timestamp lastUpdatedOn;
}
