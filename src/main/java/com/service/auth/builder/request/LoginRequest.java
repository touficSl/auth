package com.service.auth.builder.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.service.auth.config.SanitizedStringDeserializer;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	@NotBlank
	private String username;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	@NotBlank
	private String password;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	@NotBlank
	private String captchaToken;

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

	public String getCaptchaToken() {
		return captchaToken;
	}

	public void setCaptchaToken(String captchaToken) {
		this.captchaToken = captchaToken;
	}
}
