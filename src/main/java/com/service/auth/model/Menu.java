package com.service.auth.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "menu", uniqueConstraints = { @UniqueConstraint(columnNames = "id") })
public class Menu {
	@Id
	private String id;

	@NotBlank
	@Size(max = 20)
	@Column(name = "lang")
	private String lang;

	@Size(max = 200)
	private String name;

	private String href;

	private String onclick;
	
	private String icon;

	@Column(name = "parent_id")
	private String parentId;

	private Date date_time;

//	I added this field because of the translation, in order not to repeat the authorized APIs for each page translation, in case we want exception, we can change the auth_id
	@Column(name = "auth_id")
	private String authId;
	
	private int order;

	// default menu actions if he has
	private boolean isget;
	private boolean ispost;
	private boolean isupdate;
	private boolean isdelete;
//	For specific requirements, for example if they want some API access to specific user roles and not for other role, like manager can change users mobile/email, but users can't
	private boolean isconfiguration;
	private String additionalconfig;

	private boolean show;
	private boolean showdropdownlist;
	private boolean opendropdownlist;

	public Menu() {
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getDate_time() {
		return date_time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
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

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public String getAdditionalconfig() {
		return additionalconfig;
	}

	public void setAdditionalconfig(String additionalconfig) {
		this.additionalconfig = additionalconfig;
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
