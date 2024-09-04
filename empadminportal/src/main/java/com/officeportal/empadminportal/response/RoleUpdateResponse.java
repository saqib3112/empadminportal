package com.officeportal.empadminportal.response;

import java.time.LocalDateTime;

import com.officeportal.empadminportal.model.Role;

public class RoleUpdateResponse {

	private long id;
	private String username;
	private String email;
	private Role role;
	private String message;

	public RoleUpdateResponse(long id, String username, String email, Role role, String message) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.message = message;
	}
	
	

	public RoleUpdateResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}