package com.horiaconstantin.springboot.webappseed.service;

import com.horiaconstantin.springboot.webappseed.feature.ApplicationException;

public class TransferException extends ApplicationException {
	public TransferException(String message) {
		super(message);
	}
}
