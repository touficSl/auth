package com.service.auth.builder.response;

public class VerifyUsernameResponse {

	private boolean success;
	private boolean sendsms;
	private String authtype;
	private String uaepasscallback;
	private boolean registerpass;
	
	public VerifyUsernameResponse() {
		super();
	}
	public VerifyUsernameResponse(boolean sendsms, String authtype, String uaepasscallback, boolean registerpass) {
		super();
		this.success = true;
		this.sendsms = sendsms;
		this.authtype = authtype;
		this.uaepasscallback = uaepasscallback;
		this.registerpass = registerpass;
	}
	public boolean isSuccess() {
		return success;
	}
	public boolean isSendsms() {
		return sendsms;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setSendsms(boolean sendsms) {
		this.sendsms = sendsms;
	}
	public String getAuthtype() {
		return authtype;
	}
	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	public String getUaepasscallback() {
		return uaepasscallback;
	}
	public void setUaepasscallback(String uaepasscallback) {
		this.uaepasscallback = uaepasscallback;
	}
	public boolean isRegisterpass() {
		return registerpass;
	}
	public void setRegisterpass(boolean registerpass) {
		this.registerpass = registerpass;
	}
}
