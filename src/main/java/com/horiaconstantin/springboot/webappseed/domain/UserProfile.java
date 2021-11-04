package com.horiaconstantin.springboot.webappseed.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(indexes = @Index(columnList = "username"))
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String username;

	@NotBlank
	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@OneToMany(mappedBy = "userProfile")
	private Set<Transaction> transactions;

	public String getUsername() {
		return username;
	}

	public UserProfile setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserProfile setPassword(String password) {
		this.password = password;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserProfile userProfile = (UserProfile) o;

		if (id != userProfile.id) return false;
		if (!username.equals(userProfile.username)) return false;
		return password.equals(userProfile.password);
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + username.hashCode();
		result = 31 * result + password.hashCode();
		return result;
	}
}

