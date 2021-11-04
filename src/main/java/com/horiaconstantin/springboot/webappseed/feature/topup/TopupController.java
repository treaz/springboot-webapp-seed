package com.horiaconstantin.springboot.webappseed.feature.topup;

import com.horiaconstantin.springboot.webappseed.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TopupController {

	private final TransactionService transactionService;

	public TopupController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping(value = "/topup")
	public ResponseEntity<?> topup(@Valid @RequestBody TopupRequest topup) {
		return ResponseEntity.ok(transactionService.executeTopupTransaction(topup.getAmount()));
	}
}