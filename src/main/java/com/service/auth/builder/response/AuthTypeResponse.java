package com.service.auth.builder.response;

public class AuthTypeResponse {
	private String name;
	private boolean isrequirepassword;

	public AuthTypeResponse(String name, boolean isrequirepassword) {
		this.name = name;
		this.isrequirepassword = isrequirepassword;
	}

	public String getName() {
		return name;
	}

	public boolean isRequirepassword() {
		return isrequirepassword;
	}
}