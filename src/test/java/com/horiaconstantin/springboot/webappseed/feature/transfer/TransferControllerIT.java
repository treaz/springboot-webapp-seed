package com.horiaconstantin.springboot.webappseed.feature.transfer;

import com.horiaconstantin.springboot.webappseed.domain.User;
import com.horiaconstantin.springboot.webappseed.repository.UserRepository;
import com.horiaconstantin.springboot.webappseed.service.TransactionService;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class TransferControllerIT {
	private static final String PASSWORD = "pass";
	private static final String USER = "username";
	private static final String USER2 = "username2";

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private MockMvc mvc;

	static User user;
	static User user2;

	@BeforeAll
	public static void init(@Autowired UserRepository userRepository) {
		user = new User()
				.setUsername(USER)
				.setPassword(PASSWORD);
		userRepository.save(user);
		user2 = new User()
				.setUsername(USER2)
				.setPassword(PASSWORD);
		userRepository.save(user2);
	}

	@WithMockUser(username = USER, password = PASSWORD)
	@Test
	public void testTransfer() throws Exception {
		transactionService.executeTopupTransactionFor(1000, user);

		mvc.perform(post("/transfer")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"amount\": 100, \"receiverUsername\": \"" + USER2 + "\"}")
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.sender", is(USER)))
				.andExpect(jsonPath("$.receiver", is(USER2)))
				.andExpect(jsonPath("$.amount", is(100)));

	}
}