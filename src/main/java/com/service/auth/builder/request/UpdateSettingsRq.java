package com.service.auth.builder.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.service.auth.config.SanitizedStringDeserializer;

import jakarta.validation.constraints.NotBlank;

public class UpdateSettingsRq {

	private boolean registeruaepassusers;

	private boolean registerldapusers;

	@NotBlank
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String suppportemail;

	@NotBlank
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String passdefaultrole;

	@NotBlank
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String uaepassdefaultrole;

	@NotBlank
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String ldapdefaultrole;
	
	private boolean uaepasssaveeid;

	public boolean isRegisteruaepassusers() {
		return registeruaepassusers;
	}

	public void setRegisteruaepassusers(boolean registeruaepassusers) {
		this.registeruaepassusers = registeruaepassusers;
	}

	public boolean isRegisterldapusers() {
		return registerldapusers;
	}

	public void setRegisterldapusers(boolean registerldapusers) {
		this.registerldapusers = registerldapusers;
	}

	public String getSuppportemail() {
		return suppportemail;
	}

	public void setSuppportemail(String suppportemail) {
		this.suppportemail = suppportemail;
	}

	public String getPassdefaultrole() {
		return passdefaultrole;
	}

	public void setPassdefaultrole(String passdefaultrole) {
		this.passdefaultrole = passdefaultrole;
	}

	public String getUaepassdefaultrole() {
		return uaepassdefaultrole;
	}

	public void setUaepassdefaultrole(String uaepassdefaultrole) {
		this.uaepassdefaultrole = uaepassdefaultrole;
	}

	public boolean isUaepasssaveeid() {
		return uaepasssaveeid;
	}

	public void setUaepasssaveeid(boolean uaepasssaveeid) {
		this.uaepasssaveeid = uaepasssaveeid;
	}

	public String getLdapdefaultrole() {
		return ldapdefaultrole;
	}

	public void setLdapdefaultrole(String ldapdefaultrole) {
		this.ldapdefaultrole = ldapdefaultrole;
	}
}
