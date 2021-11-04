package com.horiaconstantin.springboot.webappseed.feature.transfer;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;

public class TransferResponse {

	@NotBlank
	private String sender;
	@NotBlank
	private String receiver;
	@NonNull
	private long amount;

	public String getSender() {
		return sender;
	}

	public TransferResponse setSender(String sender) {
		this.sender = sender;
		return this;
	}

	public String getReceiver() {
		return receiver;
	}

	public TransferResponse setReceiver(String receiver) {
		this.receiver = receiver;
		return this;
	}

	public long getAmount() {
		return amount;
	}

	public TransferResponse setAmount(long amount) {
		this.amount = amount;
		return this;
	}
}
