package com.horiaconstantin.springboot.webappseed.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
@Table
public class Transaction {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@PastOrPresent
	@Column(nullable = false)
	private Date transactionDate;

	@NotNull
	@Column(nullable = false)
	private long openingBalance;

	@NotNull
	@Column(nullable = false)
	private long transactionAmount;

	public User getUser() {
		return user;
	}

	public Transaction setUser(User user) {
		this.user = user;
		return this;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public Transaction setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
		return this;
	}

	public long getOpeningBalance() {
		return openingBalance;
	}

	public Transaction setOpeningBalance(long openingBalance) {
		this.openingBalance = openingBalance;
		return this;
	}

	public long getTransactionAmount() {
		return transactionAmount;
	}

	public Transaction setTransactionAmount(long transactionAmount) {
		this.transactionAmount = transactionAmount;
		return this;
	}

	@Override
	public String toString() {
		return "Transaction{" +
				"id=" + id +
				", user=" + user +
				", transactionDate=" + transactionDate +
				", openingBalance=" + openingBalance +
				", TransactionAmount=" + transactionAmount +
				'}';
	}
}
