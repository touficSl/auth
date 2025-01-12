package com.service.auth.builder.response;

import java.util.List;

import com.service.auth.model.Roles;
import com.service.auth.model.Users;

public class RolesUsersResponse {
	
	private String role;
	
	private String auth_type;

	private Boolean require2fa;

	private String position;
	private String level;
	private String parentrole;
	
	private List<Users> users;
	
	public RolesUsersResponse(Roles r, List<Users> userslist) {
		super();
		this.role = r.getUser_role();
		this.auth_type = r.getAuth_type();
		this.require2fa = r.getRequire2fa();
		this.users = userslist;
	}

	public RolesUsersResponse() {
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAuth_type() {
		return auth_type;
	}

	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}

	public Boolean getRequire2fa() {
		return require2fa;
	}

	public void setRequire2fa(Boolean require2fa) {
		this.require2fa = require2fa;
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

	public List<Users> getUsers() {
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}
}
