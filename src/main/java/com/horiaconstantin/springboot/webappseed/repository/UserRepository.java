package com.horiaconstantin.springboot.webappseed.repository;

import com.horiaconstantin.springboot.webappseed.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}