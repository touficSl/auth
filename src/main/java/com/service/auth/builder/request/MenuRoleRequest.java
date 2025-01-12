package com.service.auth.builder.request;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.service.auth.config.SanitizedStringDeserializer;

public class MenuRoleRequest {

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String userrole;
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String name;
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String auth_type;
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String require2fa;
    

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String level;
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String position;
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String parentrole;

	private List<AllowedMenu> allowedmenulist;
	
	public String getUserrole() {
		return userrole;
	}
	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}
	public String getAuth_type() {
		return auth_type;
	}
	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}
	public String getRequire2fa() {
		return require2fa;
	}
	public void setRequire2fa(String require2f) {
		this.require2fa = require2f;
	}
	public List<AllowedMenu> getAllowedmenulist() {
		return allowedmenulist;
	}
	public void setAllowedmenulist(List<AllowedMenu> allowedmenulist) {
		this.allowedmenulist = allowedmenulist;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getParentrole() {
		return parentrole;
	}
	public void setParentrole(String parentrole) {
		this.parentrole = parentrole;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
