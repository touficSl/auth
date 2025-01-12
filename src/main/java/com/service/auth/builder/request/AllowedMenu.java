package com.service.auth.builder.request;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.service.auth.config.SanitizedStringDeserializer;

public class AllowedMenu {

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
	private String menuCode;
	private List <AllowedMenu> children;
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public List<AllowedMenu> getChildren() {
		return children;
	}
	public void setChildren(List<AllowedMenu> children) {
		this.children = children;
	}
}
