package com.service.auth.builder.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.service.auth.config.SanitizedStringDeserializer;

public class UpdateUserReq {

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String first_name;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String last_name;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String username;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String newusername;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String password;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String user_role;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String email;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String mobile_no;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String hire_date;

	private Double salary;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String first_name_ar;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String last_name_ar;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String captchaToken;
    
    private boolean bypass3rdpartyauth;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String longitude;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String lattitude;

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

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getUser_role() {
		return user_role;
	}

	public String getEmail() {
		return email;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public String getHire_date() {
		return hire_date;
	}

	public Double getSalary() {
		return salary;
	}

	public String getFirst_name_ar() {
		return first_name_ar;
	}

	public String getLast_name_ar() {
		return last_name_ar;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public void setFirst_name_ar(String first_name_ar) {
		this.first_name_ar = first_name_ar;
	}

	public void setLast_name_ar(String last_name_ar) {
		this.last_name_ar = last_name_ar;
	}

	public String getNewusername() {
		return newusername;
	}

	public void setNewusername(String newusername) {
		this.newusername = newusername;
	}

	public boolean isBypass3rdpartyauth() {
		return bypass3rdpartyauth;
	}

	public void setBypass3rdpartyauth(boolean bypass3rdpartyauth) {
		this.bypass3rdpartyauth = bypass3rdpartyauth;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLattitude() {
		return lattitude;
	}

	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}

}
