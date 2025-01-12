package com.service.auth.builder.response;

import com.service.auth.config.Constants;

public class CountResponse {

	private long count;
	private String status;
	private int code;
	private boolean success;
	
	public CountResponse() {
		super();
	}
	public CountResponse(long count) {
		super();
		this.count = count;
		this.status = Constants.SUCCESS_KEY;
		this.code = 200;
		this.success = true;
	}
	public String getStatus() {
		return status;
	}
	public int getCode() {
		return code;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
