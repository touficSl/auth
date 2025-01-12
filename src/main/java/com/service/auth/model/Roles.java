package com.service.auth.model;

import com.service.auth.config.Utils;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "roles", uniqueConstraints = { @UniqueConstraint(columnNames = "user_role") })
public class Roles {

	@NotBlank
	@Size(max = 50)
	@Column(name = "user_role")
	@Id
	private String userRole;

	private String name;
	
	private String auth_type;

	@Column(name = "require_2fa")
	private Boolean require2fa;
	
    @Transient
    private boolean requiredpassword;

	public Roles() {
	}

	public Roles(@NotBlank @Size(max = 20) String user_role, String auth_type,
			String position, String level, String parentrole, String name) {
		super();
		this.userRole = user_role;
		this.auth_type = auth_type;
		this.require2fa = true;
		this.name = name;
	}

	public String getUser_role() {
		return userRole;
	}

	public String getAuth_type() {
		
		return auth_type;
	}

	public String getUserRole() {
		return userRole;
	}

	public Boolean getRequire2fa() {
		return require2fa;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}

	public void setRequire2fa(Boolean require2fa) {
		this.require2fa = require2fa;
	}
	
	public boolean getRequiredpassword() {
		return Utils.isrequiredpass(this.auth_type);
	}

	public void setRequiredpassword(boolean requiredpassword) {
		this.requiredpassword = requiredpassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
