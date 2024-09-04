package com.officeportal.empadminportal.response;

import java.time.LocalDateTime;

import com.officeportal.empadminportal.model.Role;

public class LoginResponse {

	private String username;
	private String email;
	private Role role;
	private String token;
	private LocalDateTime expiresAt;
	
	public LoginResponse(String username, String email, Role role, String token, LocalDateTime expiresAt) {
		super();
		this.username = username;
		this.email = email;
		this.role = role;
		this.token = token;
		this.expiresAt = expiresAt;
	}

	public LoginResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}


}
