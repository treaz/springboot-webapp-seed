package com.horiaconstantin.springboot.webappseed.feature.account;

import com.horiaconstantin.springboot.webappseed.domain.Transaction;
import com.horiaconstantin.springboot.webappseed.domain.User;
import com.horiaconstantin.springboot.webappseed.repository.TransactionRepository;
import com.horiaconstantin.springboot.webappseed.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext
class AccountControllerIT {

	private static final String PASSWORD = "pass";
	private static final String USER = "username";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

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

	@WithMockUser(username = "nonexisting", password = PASSWORD)
	@Test
	public void testNonExistentAccount() throws Exception {

		mvc.perform(get("/account"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", containsString("nonexisting")));
	}

	@WithMockUser(username = USER, password = PASSWORD)
	@Test
	@Order(1)
	public void testNewAccountEmpty() throws Exception {

		mvc.perform(get("/account"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.currentBalance", is(0)))
				.andExpect(jsonPath("$.transactions").isArray())
				.andExpect(jsonPath("$.transactions", hasSize(0)));
	}

	@WithMockUser(username = USER, password = PASSWORD)
	@Test
	@Order(2)
	public void testAccountWithTransactions() throws Exception {
		Transaction s = new Transaction()
				.setUser(user)
				.setOpeningBalance(1000)
				.setTransactionAmount(200)
				.setTransactionDate(new Date());
		transactionRepository.save(s);

		mvc.perform(get("/account"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.currentBalance", is(800)))
				.andExpect(jsonPath("$.transactions").isArray())
				.andExpect(jsonPath("$.transactions", hasSize(1)));
	}
}