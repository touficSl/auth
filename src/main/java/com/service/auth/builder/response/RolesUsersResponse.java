package com.service.auth.builder.response;

import java.util.List;

import com.service.auth.model.Roles;
import com.service.auth.model.Users;

public class RolesUsersResponse {

	private String name;
	
	private String role;
	
	private String auth_type;

	private Boolean require2fa;

	private String parentrole;
	
	private List<Users> users;
	
	public RolesUsersResponse(Roles r, List<Users> userslist) {
		super();
		this.name = r.getName();
		this.role = r.getUser_role();
		this.auth_type = r.getAuth_type();
		this.require2fa = r.getRequire2fa();
		this.parentrole =r.getParentrole();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
