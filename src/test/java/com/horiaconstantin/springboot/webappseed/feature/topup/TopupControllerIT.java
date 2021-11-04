package com.horiaconstantin.springboot.webappseed.feature.topup;

import com.horiaconstantin.springboot.webappseed.domain.User;
import com.horiaconstantin.springboot.webappseed.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class TopupControllerIT {

	private static final String PASSWORD = "pass";
	private static final String USER = "username";

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

	@WithMockUser(username = USER, password = PASSWORD)
	@Test
	public void testTwoTopups() throws Exception {

		mvc.perform(post("/topup")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"amount\": 1300}")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.transactionAmount", is(-1300)))
				.andExpect(jsonPath("$.openingBalance", is(0)))
				.andExpect(jsonPath("$.transactionDate", not(empty())));

		mvc.perform(post("/topup")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"amount\": 1300}")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.transactionAmount", is(-1300)))
				.andExpect(jsonPath("$.openingBalance", is(1300)))
				.andExpect(jsonPath("$.transactionDate", not(empty())));
	}
}