package com.service.auth.builder.response;

import java.util.List;

import com.service.auth.model.Menu;
import com.service.auth.model.MenuRole;

public class MenuResponse {

	private String id;

	private String lang;

	private String name;

	private String href;

	private String onclick;

	private String icon;

	private boolean get;
	private boolean post;
	private boolean update;
	private boolean delete;
//	For specific requirements, for example if they want some API access to specific user roles and not for other role, like manager can change users mobile/email, but users can't
	private boolean configuration;
	private String additionalconfig;
	
	private boolean show;
	private boolean showdropdownlist;
	private boolean opendropdownlist;
	
	List<MenuResponse> submenu;

	public MenuResponse() {
		super();
	}

	public MenuResponse(Menu menu) {
		super();
		this.id = menu.getAuthId();
		this.lang = menu.getLang();
		this.name = menu.getName();
		this.href = menu.getHref();
		this.onclick = menu.getOnclick();
		this.icon = menu.getIcon();
		this.get = menu.isGet();
		this.post = menu.isPost();
		this.update = menu.isUpdate();
		this.delete = menu.isDelete();
		this.configuration = menu.isConfiguration();
		this.additionalconfig = menu.getAdditionalconfig();
		this.show = menu.isShow();
		this.showdropdownlist = menu.isShowdropdownlist();
		this.opendropdownlist = menu.isOpendropdownlist();
	}

	public MenuResponse(MenuRole menurole) {
		super();
		this.id = menurole.getMenu().getAuthId();
		this.lang = menurole.getMenu().getLang();
		this.name = menurole.getMenu().getName();
		this.href = menurole.getMenu().getHref();
		this.onclick = menurole.getMenu().getOnclick();
		this.icon = menurole.getMenu().getIcon();
		this.get = menurole.isGet();
		this.post = menurole.isPost();
		this.update = menurole.isUpdate();
		this.delete = menurole.isDelete();
		this.configuration = menurole.isConfiguration();
		this.additionalconfig = menurole.getMenu().getAdditionalconfig();
		this.show = menurole.getMenu().isShow();
		this.showdropdownlist = menurole.getMenu().isShowdropdownlist();
		this.opendropdownlist = menurole.getMenu().isOpendropdownlist();
	}

	public String getName() {
		return name;
	}

	public List<MenuResponse> getSubmenu() {
		return submenu;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSubmenu(List<MenuResponse> submenu) {
		this.submenu = submenu;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getHref() {
		return href;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isGet() {
		return get;
	}

	public void setGet(boolean get) {
		this.get = get;
	}

	public boolean isPost() {
		return post;
	}

	public void setPost(boolean post) {
		this.post = post;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isConfiguration() {
		return configuration;
	}

	public void setConfiguration(boolean configuration) {
		this.configuration = configuration;
	}

	public String getAdditionalconfig() {
		return additionalconfig;
	}

	public void setAdditionalconfig(String additionalconfig) {
		this.additionalconfig = additionalconfig;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isShowdropdownlist() {
		return showdropdownlist;
	}

	public void setShowdropdownlist(boolean showdropdownlist) {
		this.showdropdownlist = showdropdownlist;
	}

	public boolean isOpendropdownlist() {
		return opendropdownlist;
	}

	public void setOpendropdownlist(boolean opendropdownlist) {
		this.opendropdownlist = opendropdownlist;
	}
}
