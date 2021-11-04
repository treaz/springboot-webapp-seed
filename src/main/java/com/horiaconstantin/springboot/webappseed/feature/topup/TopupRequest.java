package com.horiaconstantin.springboot.webappseed.feature.topup;

import javax.validation.constraints.PositiveOrZero;

public class TopupRequest {

	@PositiveOrZero
	private long amount;

	public long getAmount() {
		return amount;
	}

	public TopupRequest setAmount(long amount) {
		this.amount = amount;
		return this;
	}
}
