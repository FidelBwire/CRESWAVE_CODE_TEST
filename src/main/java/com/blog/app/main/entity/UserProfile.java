package com.blog.app.main.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "UserProfiles")
@Entity
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String profileId;

	@Column(nullable = false)
	private String fullName;

	@Column(updatable = false, nullable = false)
	@CurrentTimestamp
	private Timestamp joinedOn;

	@UpdateTimestamp
	private Timestamp updatedOn;

	private Timestamp lastSignIn;
}
