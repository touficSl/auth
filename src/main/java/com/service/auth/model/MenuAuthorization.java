package com.service.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "menu_authorization", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class MenuAuthorization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "menu_auth_id")
	private String menuauthId;

	@NotBlank
	@Size(max = 450)
	private String api;

	private boolean isget;
	private boolean ispost;
	private boolean isupdate;
	private boolean isdelete;
//	For specific requirements, for example if they want some API access to specific user roles and not for other role, like manager can change users mobile/email, but users can't
	private boolean isconfiguration;
	
	private String accessibleaction;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public boolean isGet() {
		return isget;
	}
	public void setGet(boolean get) {
		this.isget = get;
	}
	public boolean isPost() {
		return ispost;
	}
	public void setPost(boolean post) {
		this.ispost = post;
	}
	public boolean isUpdate() {
		return isupdate;
	}
	public void setUpdate(boolean update) {
		this.isupdate = update;
	}
	public boolean isDelete() {
		return isdelete;
	}
	public void setDelete(boolean delete) {
		this.isdelete = delete;
	}
	public boolean isConfiguration() {
		return isconfiguration;
	}
	public void setConfiguration(boolean configuration) {
		this.isconfiguration = configuration;
	}
	public String getMenuauthId() {
		return menuauthId;
	}
	public void setMenuauthId(String menuauthId) {
		this.menuauthId = menuauthId;
	}
	public String getAccessibleaction() {
		return accessibleaction;
	}
	public void setAccessibleaction(String accessibleaction) {
		this.accessibleaction = accessibleaction;
	}
}
