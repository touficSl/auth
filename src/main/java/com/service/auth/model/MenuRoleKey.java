package com.service.auth.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class MenuRoleKey implements Serializable {
    private String menuId;
    private String userRole;

    public MenuRoleKey() {}

    public MenuRoleKey(String menuId, String userRole) {
        this.menuId = menuId;
        this.userRole = userRole;
    }

	public String getMenuId() { return menuId; }
    public void setMenuId(String menuId) { this.menuId = menuId; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }

}
