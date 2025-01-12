package com.service.auth.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@IdClass(MenuRole.class)
@Table(name = "menu_role")
public class MenuRole {

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    @EmbeddedId
    private Menu menu;

	@Size(max = 20)
	@Id
	private String user_role;

	// allowed menu role actions
	private boolean isget;
	private boolean ispost;
	private boolean isupdate;
	private boolean isdelete;
//	For specific requirements, for example if they want some API access to specific user roles and not for other role, like manager can change users mobile/email, but users can't
	private boolean isconfiguration;

	public MenuRole(Menu menu, @Size(max = 20) String user_role, boolean get, boolean post, boolean update,
			boolean delete, boolean configuration) {
		super();
		this.menu = menu;
		this.user_role = user_role;
		this.isget = get;
		this.ispost = post;
		this.isupdate = update;
		this.isdelete = delete;
		this.isconfiguration = configuration;
	}

	public MenuRole() {
	}

	public String getUser_role() {
		return user_role;
	}


	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
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
}
