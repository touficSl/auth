package com.service.auth.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "menu_role")
public class MenuRole {

    @EmbeddedId
    private MenuRoleKey id;

    @ManyToOne
    @MapsId("menuId")
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

	// allowed menu role actions
	private boolean isget;
	private boolean ispost;
	private boolean isupdate;
	private boolean isdelete;
//	For specific requirements, for example if they want some API access to specific user roles and not for other role, like manager can change users mobile/email, but users can't
	private boolean isconfiguration;

    private String accessibleactions;

	public MenuRole(Menu menu, @Size(max = 20) String user_role, boolean get, boolean post, boolean update,
			boolean delete, boolean configuration, String accessibleactions) {
		super();
		this.menu = menu;
		this.id = new MenuRoleKey(menu.getId(), user_role);
		this.isget = get;
		this.ispost = post;
		this.isupdate = update;
		this.isdelete = delete;
		this.isconfiguration = configuration;
		this.accessibleactions = accessibleactions;
	}

	public MenuRole() {
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

	public MenuRoleKey getId() {
		return id;
	}

	public void setId(MenuRoleKey id) {
		this.id = id;
	}

	public String getAccessibleactions() {
		return accessibleactions;
	}

	public void setAccessibleactions(String accessibleactions) {
		this.accessibleactions = accessibleactions;
	}
}
