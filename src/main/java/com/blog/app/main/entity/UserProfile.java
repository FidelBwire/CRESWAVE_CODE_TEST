package com.blog.app.main.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.blog.app.auth.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

	// Older versions of MySql Server use TIMESTAMP in column definition. DateTime
	// throws an exception. Uncomment as necessary
//	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
	@CurrentTimestamp
	private Timestamp joinedOn;

//	@Column(nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@UpdateTimestamp
	private Timestamp updatedOn;

//	@Column(columnDefinition = "TIMESTAMP")
	private Timestamp lastSignIn;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false)
	private User user;
}
