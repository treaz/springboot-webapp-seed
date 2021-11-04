package com.horiaconstantin.springboot.webappseed.feature.registration;

import com.horiaconstantin.springboot.webappseed.domain.User;
import com.horiaconstantin.springboot.webappseed.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class RegistrationControllerIT {

	private static final String PASSWORD = "password";
	private static final String USER = "newuser";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MockMvc mvc;

	@Test
	public void testNonExistentAccount() throws Exception {
		mvc.perform(post("/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\": \"" + USER + "\", \"password\":\"" + PASSWORD + "\"}")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username", is("newuser")));

		Optional<User> newuser = userRepository.findByUsername(USER);
		assertTrue(newuser.isPresent());
		assertEquals(USER, newuser.get().getUsername());
	}
}