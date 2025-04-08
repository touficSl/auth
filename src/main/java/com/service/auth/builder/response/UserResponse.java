package com.service.auth.builder.response;

import java.util.Date;
import java.util.List;

import com.service.auth.config.Constants;
import com.service.auth.config.Utils;
import com.service.auth.model.Authorization;
import com.service.auth.model.Roles;
import com.service.auth.model.Users;

public class UserResponse {
	private String first_name;
	private String last_name;
	private String username;
	private String user_role;
	private String email;
	private String mobile_no;
	private String hire_date;
	private Double salary;
	private String first_name_ar;
	private String last_name_ar;
	private Date date_time;
	private boolean enable;
	private boolean locked;
	private int invalidattempts;
    private List<Authorization> authorizedapis;
    private boolean canforgetpass;
	private String authtype;
	private boolean bypass3rdpartyauth;

	private String parentrole;
	
    public UserResponse() {
    }

    public UserResponse(Users users, boolean includeauthapis, boolean canforgetpass, Roles role) {
		this.first_name = users.getFirst_name();
		this.last_name = users.getLast_name();
		this.username = users.getUsername();
		this.user_role = users.getUser_role();
		this.email = users.getEmail();
		this.first_name_ar = users.getFirst_name_ar();
		this.last_name_ar = users.getLast_name_ar();
		this.enable = users.isEnable();
		this.date_time = users.getDate_time();
		this.locked = users.isLocked();
		this.invalidattempts = users.getInvalidattempts();
		this.canforgetpass = canforgetpass;
		this.authorizedapis = includeauthapis ? users.getAuthorizedapis() : null;
		this.salary = users.getSalary();
		this.hire_date = Utils.convertDateToString(users.getHire_date(), Constants.DATE_FORMAT);
		this.mobile_no = users.getMobile_no();
		this.setAuthtype(role.getAuth_type());
		this.setBypass3rdpartyauth(users.isBypass3rdpartyauth());

		this.parentrole = role.getParentrole();
    }
    
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUser_role() {
		return user_role;
	}
	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile_no() {
		return mobile_no;
	}
	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}
	public String getHire_date() {
		return hire_date;
	}
	public void setHire_date(String hire_date) {
		this.hire_date = hire_date;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getFirst_name_ar() {
		return first_name_ar;
	}
	public void setFirst_name_ar(String first_name_ar) {
		this.first_name_ar = first_name_ar;
	}
	public String getLast_name_ar() {
		return last_name_ar;
	}
	public void setLast_name_ar(String last_name_ar) {
		this.last_name_ar = last_name_ar;
	}
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public int getInvalidattempts() {
		return invalidattempts;
	}
	public void setInvalidattempts(int invalidattempts) {
		this.invalidattempts = invalidattempts;
	}
	public List<Authorization> getAuthorizedapis() {
		return authorizedapis;
	}
	public void setAuthorizedapis(List<Authorization> authorizedapis) {
		this.authorizedapis = authorizedapis;
	}

	public boolean isCanforgetpass() {
		return canforgetpass;
	}

	public void setCanforgetpass(boolean canforgetpass) {
		this.canforgetpass = canforgetpass;
	}

	public String getAuthtype() {
		return authtype;
	}

	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}

	public boolean isBypass3rdpartyauth() {
		return bypass3rdpartyauth;
	}

	public void setBypass3rdpartyauth(boolean bypass3rdpartyauth) {
		this.bypass3rdpartyauth = bypass3rdpartyauth;
	}

	public String getParentrole() {
		return parentrole;
	}

	public void setParentrole(String parentrole) {
		this.parentrole = parentrole;
	}
}
