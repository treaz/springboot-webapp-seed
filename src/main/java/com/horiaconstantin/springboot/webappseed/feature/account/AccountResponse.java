package com.horiaconstantin.springboot.webappseed.feature.account;

import com.horiaconstantin.springboot.webappseed.domain.Transaction;

import java.util.List;

public class AccountResponse {

	private List<Transaction> transactions;
	private long currentBalance;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public AccountResponse setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
		return this;
	}

	public long getCurrentBalance() {
		return currentBalance;
	}

	public AccountResponse setCurrentBalance(long currentBalance) {
		this.currentBalance = currentBalance;
		return this;
	}
}
