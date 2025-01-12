package com.service.auth.builder.response;

import com.service.auth.model.Roles;

public class JwtResponse {
	private String token;
	private Long id;
	private String username;
	private String email;
	private String role;
	private String fullname;
	private String fullnamear;

	private String position;
	private String level;
	private String parentrole;

	private String refreshtoken;
	private String key;
	
	private boolean sendsms;

	private String accessexpirydate;
	private String refreshexpirydate;

	public JwtResponse(String accessToken, Long id, String username, String email, String role, boolean sendsms, String accessexpirydate, String fullname, String fullnamear, Roles userrole) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.sendsms = sendsms;
		this.accessexpirydate = accessexpirydate;
		this.fullname = fullname;
		this.fullnamear = fullnamear;
	}

	public JwtResponse(String accessToken, Long id, String username, String email, String role, String refreshtoken, String key, String accessexpirydate, String refreshexpirydate, String fullname, String fullnamear, Roles userrole) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.role = role;
		this.refreshtoken = refreshtoken;
		this.key = key;
		this.accessexpirydate = accessexpirydate;
		this.refreshexpirydate = refreshexpirydate;
		this.fullname = fullname;
		this.fullnamear = fullnamear;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public String getRefreshtoken() {
		return refreshtoken;
	}

	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isSendsms() {
		return sendsms;
	}

	public void setSendsms(boolean sendsms) {
		this.sendsms = sendsms;
	}

	public String getAccessexpirydate() {
		return accessexpirydate;
	}

	public void setAccessexpirydate(String accessexpirydate) {
		this.accessexpirydate = accessexpirydate;
	}

	public String getRefreshexpirydate() {
		return refreshexpirydate;
	}

	public void setRefreshexpirydate(String refreshexpirydate) {
		this.refreshexpirydate = refreshexpirydate;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getFullnamear() {
		return fullnamear;
	}

	public void setFullnamear(String fullnamear) {
		this.fullnamear = fullnamear;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getParentrole() {
		return parentrole;
	}

	public void setParentrole(String parentrole) {
		this.parentrole = parentrole;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
