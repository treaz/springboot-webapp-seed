package com.horiaconstantin.springboot.webappseed.repository;

import com.horiaconstantin.springboot.webappseed.domain.UserProfile;
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
class UserProfileRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository repository;

	@Test
	public void noUsersInEmptyReposity() {
		Iterable<UserProfile> users = repository.findAll();

		assertThat(users).isEmpty();
	}

	@Test
	public void testFindsOneUser() {
		UserProfile userProfile = new UserProfile()
				.setUsername("user")
				.setPassword("password");
		entityManager.persist(userProfile);

		Iterable<UserProfile> users = repository.findAll();

		assertThat(users).hasSize(1).contains(userProfile);
	}

	@Test
	public void testFindByUsernameExistsInRepo() {
		UserProfile userProfile = new UserProfile()
				.setUsername("user")
				.setPassword("password");
		entityManager.persist(userProfile);

		Optional<UserProfile> foundUser = repository.findByUsername("user");

		assertTrue(foundUser.isPresent());
		assertEquals("user", foundUser.get().getUsername());
	}

	@Test
	public void testFindByUsernameNotExistsInRepo() {
		Optional<UserProfile> foundUser = repository.findByUsername("nonexisting");

		assertFalse(foundUser.isPresent());
	}

	@Test
	public void testFindByUsernameNull() {
		Optional<UserProfile> foundUser = repository.findByUsername(null);

		assertFalse(foundUser.isPresent());
	}

	@Test
	public void testFindByUsernameEmpty() {
		Optional<UserProfile> foundUser = repository.findByUsername("");

		assertFalse(foundUser.isPresent());
	}


}