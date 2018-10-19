package com.codygordon.acechatredux.models;

public class UserCredentials {

	public String email;
	public String username;
	public String password;
	
	public UserCredentials(String email, String username, String password) {
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
	public UserCredentials(String email, String password) {
		this.email = email;
		this.password = password;
	}
}