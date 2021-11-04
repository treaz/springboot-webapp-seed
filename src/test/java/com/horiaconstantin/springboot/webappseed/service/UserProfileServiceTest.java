package com.horiaconstantin.springboot.webappseed.service;

import com.horiaconstantin.springboot.webappseed.domain.UserProfile;
import com.horiaconstantin.springboot.webappseed.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceTest {

	@Mock
	private Authentication authentication;
	@Mock
	private SecurityContext securityContext;
	@Mock
	private UserRepository userRepository;

	private UserService userService;

	@BeforeEach
	private void beforeEach() {
		userService = new UserService(userRepository);
	}

	@Test
	void getCurrentUser() {
		String username = "user";
		String password = "password";
		mockSecurityContextHolderUser(username, password);

		UserProfile expected = new UserProfile()
				.setUsername(username)
				.setPassword(password);

		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(expected));
		UserProfile currentUserProfile = userService.getCurrentUser();

		assertEquals(expected.getUsername(), currentUserProfile.getUsername());
	}

	@Test
	void getUser() {
		UserProfile expected = new UserProfile()
				.setUsername("other")
				.setPassword("password");

		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(expected));
		UserProfile currentUserProfile = userService.getUser("other");

		assertEquals(expected.getUsername(), currentUserProfile.getUsername());
	}

	@Test
	void getUserThrows() {
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> userService.getUser("other"));
	}

	private void mockSecurityContextHolderUser(String username, String password) {
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		org.springframework.security.core.userdetails.User user =
				new org.springframework.security.core.userdetails.User(username, password, new ArrayList<>());
		when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
	}
}