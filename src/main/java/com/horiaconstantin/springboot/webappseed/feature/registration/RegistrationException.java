package com.horiaconstantin.springboot.webappseed.feature.registration;

import com.horiaconstantin.springboot.webappseed.feature.ApplicationException;

public class RegistrationException extends ApplicationException {
	public RegistrationException(String message) {
		super(message);
	}
}
