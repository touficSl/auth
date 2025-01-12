package com.service.auth.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.auth.config.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tokens", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class Tokens {
	@Id
	private String id;

	@NotBlank
	@Size(max = 500)
	@Column(name = "username")
	private String username;
	
	private String accesstoken;
	
	private String refreshtoken;
	
	private String refreshkey;
	
	private String device;
	
	private String ip;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATETIME_FORMAT)
	private Date access_expiry_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATETIME_FORMAT)
	private Date refresh_expiry_date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATETIME_FORMAT)
	private Date revoked_date_time;
	
	private String reason;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATETIME_FORMAT)
	private Date date_time;

	public Tokens() {
	}
	
	public Tokens(String id, @NotBlank @Size(max = 500) String username, String accesstoken, String refreshtoken, String device, String ip, Date access_expiry_date, String reason, Date refresh_expiry_date, String refreshkey) {
		super();
		this.id = id;
		this.username = username;
		this.accesstoken = accesstoken;
		this.refreshtoken = refreshtoken;
		this.setRefreshkey(refreshkey);
		this.device = device;
		this.ip = ip;
		this.access_expiry_date = access_expiry_date;
		this.reason = reason;
		this.refresh_expiry_date = refresh_expiry_date;
		this.date_time = new Date();
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public String getRefreshtoken() {
		return refreshtoken;
	}

	public String getDevice() {
		return device;
	}

	public String getIp() {
		return ip;
	}

	public Date getRevoked_date_time() {
		return revoked_date_time;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setRevoked_date_time(Date revoked_date_time) {
		this.revoked_date_time = revoked_date_time;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getAccess_expiry_date() {
		return access_expiry_date;
	}

	public Date getRefresh_expiry_date() {
		return refresh_expiry_date;
	}

	public void setAccess_expiry_date(Date access_expiry_date) {
		this.access_expiry_date = access_expiry_date;
	}

	public void setRefresh_expiry_date(Date refresh_expiry_date) {
		this.refresh_expiry_date = refresh_expiry_date;
	}

	public String getRefreshkey() {
		return refreshkey;
	}

	public void setRefreshkey(String refreshkey) {
		this.refreshkey = refreshkey;
	}
}
