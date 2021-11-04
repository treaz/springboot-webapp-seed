package com.horiaconstantin.springboot.webappseed.service;

import com.horiaconstantin.springboot.webappseed.feature.ApplicationException;

public class UserNotFoundException extends ApplicationException {
	public UserNotFoundException(String message) {
		super(message);
	}
}
