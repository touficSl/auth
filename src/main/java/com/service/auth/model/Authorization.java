package com.service.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@IdClass(AuthorizationId.class)
@Table(name = "authorization", uniqueConstraints = { @UniqueConstraint(columnNames = {"userRole", "api"}) })
public class Authorization {

	@NotBlank
	@Size(max = 20)
	@Column(name = "user_role")
	@Id
	private String userRole;

	@NotBlank
	@Size(max = 255)
	@Id
	private String menuauthid;

	@NotBlank
	@Size(max = 450)
	@Id
	private String api;

	private boolean enable;
	
	public Authorization(@NotBlank @Size(max = 20) String userRole, @NotBlank @Size(max = 450) String api,
			boolean enable, @NotBlank @Size(max = 450) String menuauthid) {
		super();
		this.userRole = userRole;
		this.api = api;
		this.enable = enable;
		this.menuauthid = menuauthid;
	}

	public Authorization() {
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getMenuauthid() {
		return menuauthid;
	}

	public void setMenuauthid(String menuauthid) {
		this.menuauthid = menuauthid;
	}
}
