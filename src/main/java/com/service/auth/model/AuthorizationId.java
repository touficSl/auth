package com.service.auth.model;

import java.io.Serializable;
import java.util.Objects;

public class AuthorizationId implements Serializable {
    private String userRole;
    private String menuauthid;
    private String api;

    // Default constructor
    public AuthorizationId() {}

    // Constructor
    public AuthorizationId(String userRole, String menuauthid, String api) {
        this.userRole = userRole;
        this.menuauthid = menuauthid;
        this.api = api;
    }

    // Getters and Setters
    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }

    public String getMenuauthid() { return menuauthid; }
    public void setMenuauthid(String menuauthid) { this.menuauthid = menuauthid; }

    public String getApi() { return api; }
    public void setApi(String api) { this.api = api; }

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationId that = (AuthorizationId) o;
        return Objects.equals(userRole, that.userRole) &&
               Objects.equals(menuauthid, that.menuauthid) &&
               Objects.equals(api, that.api);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRole, menuauthid, api);
    }
}
