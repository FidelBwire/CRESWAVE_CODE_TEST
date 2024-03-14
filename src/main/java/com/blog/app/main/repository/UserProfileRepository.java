package com.blog.app.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.app.main.entity.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

	Optional<UserProfile> findProfileByUserId(String userId);
}
