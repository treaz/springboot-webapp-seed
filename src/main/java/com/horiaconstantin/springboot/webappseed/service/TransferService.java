package com.horiaconstantin.springboot.webappseed.service;

import com.horiaconstantin.springboot.webappseed.domain.UserProfile;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class TransferService {

	private final UserService userService;
	private final TransactionService transactionService;

	public TransferService(UserService userRepository, TransactionService transactionRepository) {
		this.userService = userRepository;
		this.transactionService = transactionRepository;
	}

	public long transfer(long amount, String username) {
		UserProfile receiver = userService.getUser(username);
		UserProfile currentUserProfile = userService.getCurrentUser();

		if (receiver.equals(currentUserProfile)) {
			throw new TransferException("Only transferring money to other users is allowed.");
		}

		long accountBalance = transactionService.getAccountBalance();
		if (accountBalance - amount < 0) {
			throw new TransferException(
					format("Cannot transfer an amount (%s) higher than the current account balance (%s)",
							amount, accountBalance));
		}
		transactionService.executeDeductionTransaction(amount);

		transactionService.executeTopupTransactionFor(amount, receiver);
		return amount;
	}
}
