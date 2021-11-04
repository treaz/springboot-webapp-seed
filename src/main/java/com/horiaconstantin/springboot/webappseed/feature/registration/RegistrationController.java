package com.horiaconstantin.springboot.webappseed.feature.registration;

import com.horiaconstantin.springboot.webappseed.domain.User;
import com.horiaconstantin.springboot.webappseed.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RegistrationController {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> saveUser(@RequestBody UserDto user) {
		return ResponseEntity.ok(save(user));
	}

	User save(UserDto user) {
		String username = user.getUsername();
		if (userRepository.findByUsername(username).isPresent()) {
			throw new RegistrationException(String.format("User %s already exists", username));
		}
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(newUser);
	}
}
