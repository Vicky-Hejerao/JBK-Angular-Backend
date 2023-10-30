package com.jbk.controller;


public class LoginResponse {
	private String message;
	private Registration user;

	public LoginResponse(String message, Registration user) {
		this.message = message;
		this.user = user;
	}

	@Override
	public String toString() {
		return "LoginResponse [message=" + message + ", user=" + user + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Registration getUser() {
		return user;
	}

	public void setUser(Registration user) {
		this.user = user;
	}

	public LoginResponse() {

	}
}
