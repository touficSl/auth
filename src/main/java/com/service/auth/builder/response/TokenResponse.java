package com.service.auth.builder.response;

import com.service.auth.config.Constants;

public class TokenResponse {

	private String token;
	private String status;
	private int code;
	private boolean success;
	
	public TokenResponse() {
		super();
	}
	public TokenResponse(String token) {
		super();
		this.token = token;
		this.status = Constants.SUCCESS_KEY;
		this.code = 200;
		this.success = true;
	}
	public String getToken() {
		return token;
	}
	public String getStatus() {
		return status;
	}
	public int getCode() {
		return code;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
