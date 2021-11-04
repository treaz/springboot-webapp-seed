package com.horiaconstantin.springboot.webappseed.feature.transfer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class TransferRequest {

	@NotBlank
	private String receiverUsername;
	@PositiveOrZero
	private long amount;

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public TransferRequest setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
		return this;
	}

	public long getAmount() {
		return amount;
	}

	public TransferRequest setAmount(long amount) {
		this.amount = amount;
		return this;
	}
}
