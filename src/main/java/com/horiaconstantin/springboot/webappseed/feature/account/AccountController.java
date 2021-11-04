package com.horiaconstantin.springboot.webappseed.feature.account;

import com.horiaconstantin.springboot.webappseed.domain.Transaction;
import com.horiaconstantin.springboot.webappseed.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

	private final TransactionService transactionService;

	public AccountController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@GetMapping(value = "/account")
	public ResponseEntity<?> account() {
		return ResponseEntity.ok(getAccount());
	}

	AccountResponse getAccount() {
		List<Transaction> userTransactionsOrdered = transactionService.findByUserOrderByTransactionDateDesc();

		AccountResponse accountResponse = new AccountResponse();
		accountResponse.setTransactions(userTransactionsOrdered);
		accountResponse.setCurrentBalance(transactionService.getAccountBalance());
		return accountResponse;
	}
}