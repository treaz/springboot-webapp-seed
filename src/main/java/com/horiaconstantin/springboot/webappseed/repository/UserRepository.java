package com.horiaconstantin.springboot.webappseed.repository;

import com.horiaconstantin.springboot.webappseed.domain.UserProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserProfile, Integer> {
	Optional<UserProfile> findByUsername(String username);
}