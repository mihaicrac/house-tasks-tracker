package com.house.taskstracker.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAddRequest {

	@NotNull(message = "username required")
	@Size(min = 1, max = 40, message = "username '${validatedValue}' size must be between {min} and {max}")
	String username;

	@Size(max = 40, message = "firstname '${validatedValue}' max size must be {max}")
	String firstname;

	@Size(max = 40, message = "lastname '${validatedValue}' max size must be {max}")
	String lastname;

	@NotNull(message = "password required")
	String password;

	@NotNull(message = "email required")
	@Size(min = 1, max = 254, message = "email '${validatedValue}' size must be between {min} and {max}")
	String email;
	
	
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
