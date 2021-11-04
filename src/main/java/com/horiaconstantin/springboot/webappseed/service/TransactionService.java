package com.horiaconstantin.springboot.webappseed.service;

import com.horiaconstantin.springboot.webappseed.domain.Transaction;
import com.horiaconstantin.springboot.webappseed.domain.User;
import com.horiaconstantin.springboot.webappseed.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

	private static final long ACCOUNT_EMPTY = 0L;

	private final UserService userRepository;
	private final TransactionRepository transactionRepository;

	public TransactionService(UserService userRepository, TransactionRepository transactionRepository) {
		this.userRepository = userRepository;
		this.transactionRepository = transactionRepository;
	}

	public long getAccountBalance() {
		User user = userRepository.getCurrentUser();
		return transactionRepository.findFirstByUserOrderByTransactionDateDesc(user)
				.map(lastTransaction -> lastTransaction.getOpeningBalance() - lastTransaction.getTransactionAmount())
				.orElse(ACCOUNT_EMPTY);
	}

	public long getAccountBalance(User user) {
		return transactionRepository.findFirstByUserOrderByTransactionDateDesc(user)
				.map(lastTransaction -> lastTransaction.getOpeningBalance() - lastTransaction.getTransactionAmount())
				.orElse(ACCOUNT_EMPTY);
	}


	public List<Transaction> findByUserOrderByTransactionDateDesc() {
		User user = userRepository.getCurrentUser();
		return transactionRepository.findByUserOrderByTransactionDateDesc(user);
	}

	public Transaction executeTopupTransaction(long amount) {
		return executeTopupTransactionFor(amount, userRepository.getCurrentUser());
	}

	public Transaction executeDeductionTransaction(long amount) {
		Transaction deductionTransaction = new Transaction();
		deductionTransaction.setUser(userRepository.getCurrentUser());
		deductionTransaction.setTransactionAmount(amount);
		deductionTransaction.setTransactionDate(new Date());
		deductionTransaction.setOpeningBalance(getAccountBalance());
		return transactionRepository.save(deductionTransaction);
	}

	public Transaction executeTopupTransactionFor(long amount, User recipient) {
		Transaction topupTransaction = new Transaction();
		topupTransaction.setUser(recipient);
		topupTransaction.setTransactionAmount(-amount);
		topupTransaction.setTransactionDate(new Date());
		topupTransaction.setOpeningBalance(getAccountBalance(recipient));
		return transactionRepository.save(topupTransaction);
	}
}
