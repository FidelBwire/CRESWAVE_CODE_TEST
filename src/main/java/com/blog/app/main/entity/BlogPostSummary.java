package com.blog.app.main.entity;

import java.sql.Timestamp;

import com.blog.app.auth.entity.User;

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
public class BlogPostSummary {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private User author;
	private String title;
	private Timestamp createdOn;
	private Timestamp lastUpdatedOn;
}
