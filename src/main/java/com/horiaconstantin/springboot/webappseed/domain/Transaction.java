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
	@JoinColumn(name = "userProfile_id", nullable = false)
	private UserProfile userProfile;

	@PastOrPresent
	@Column(nullable = false)
	private Date transactionDate;

	@NotNull
	@Column(nullable = false)
	private long openingBalance;

	@NotNull
	@Column(nullable = false)
	private long transactionAmount;

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public Transaction setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
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
				", userProfile=" + userProfile +
				", transactionDate=" + transactionDate +
				", openingBalance=" + openingBalance +
				", TransactionAmount=" + transactionAmount +
				'}';
	}
}
