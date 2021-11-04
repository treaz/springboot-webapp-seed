package com.horiaconstantin.springboot.webappseed.repository;

import com.horiaconstantin.springboot.webappseed.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;

	@Test
	public void noUsersInEmptyReposity() {
		Iterable<User> users = repository.findAll();

		assertThat(users).isEmpty();
	}

	@Test
	public void testFindsOneUser() {
		User user = new User()
				.setUsername("user")
				.setPassword("password");
		entityManager.persist(user);

		Iterable<User> users = repository.findAll();

		assertThat(users).hasSize(1).contains(user);
	}

	@Test
	public void testFindByUsernameExistsInRepo() {
		User user = new User()
				.setUsername("user")
				.setPassword("password");
		entityManager.persist(user);

		Optional<User> foundUser = repository.findByUsername("user");

		assertTrue(foundUser.isPresent());
		assertEquals("user", foundUser.get().getUsername());
	}

	@Test
	public void testFindByUsernameNotExistsInRepo() {
		Optional<User> foundUser = repository.findByUsername("nonexisting");

		assertFalse(foundUser.isPresent());
	}

	@Test
	public void testFindByUsernameNull() {
		Optional<User> foundUser = repository.findByUsername(null);

		assertFalse(foundUser.isPresent());
	}

	@Test
	public void testFindByUsernameEmpty() {
		Optional<User> foundUser = repository.findByUsername("");

		assertFalse(foundUser.isPresent());
	}


}