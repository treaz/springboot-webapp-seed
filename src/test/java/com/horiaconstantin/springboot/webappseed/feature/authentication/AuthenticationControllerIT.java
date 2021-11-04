package com.horiaconstantin.springboot.webappseed.feature.authentication;

import com.horiaconstantin.springboot.webappseed.domain.User;
import com.horiaconstantin.springboot.webappseed.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class AuthenticationControllerIT {

	private static final String PASSWORD = "password";
	private static final String USER = "username";
	private static final String NONUSER = "nonusername";

	@Autowired
	private UserRepository userRepository;

	@MockBean
	private AuthenticationManager authenticationManager;

	@Autowired
	private MockMvc mvc;

	static User user;

	@BeforeAll
	public static void init(@Autowired UserRepository userRepository) {
		user = new User()
				.setUsername(USER)
				.setPassword(PASSWORD);
		userRepository.save(user);
	}

	@Test
	public void testAuthenticateExistingUser() throws Exception {
		mvc.perform(post("/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\": \"" + USER + "\", \"password\":\"" + PASSWORD + "\"}")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token", is(notNullValue())));
	}

	@Test
	public void testAuthenticateNonExistingUser() throws Exception {
		mvc.perform(post("/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"username\": \"" + NONUSER + "\", \"password\":\"" + PASSWORD + "\"}")
				)
				.andExpect(status().isUnauthorized());
	}
}