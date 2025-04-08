package com.service.auth.builder.response;

import java.util.List;

import com.service.auth.model.Roles;
import com.service.auth.model.Teams;

public class TeamsRolesResponse {

    private String code;
    
    private String nameen;

    private String namear;

    private String description;
    
    private List<Roles> rolelist;
    
	public TeamsRolesResponse() {
		super();
	}
	public TeamsRolesResponse(String teamcode, List<Roles> rolelist) {
		super();
		this.code = teamcode;
		this.rolelist = rolelist;
	}

	public TeamsRolesResponse(Teams team, List<Roles> rolelist) {
		super();
		this.code = team.getCode();
		this.nameen = team.getNameen();
		this.namear = team.getNamear();
		this.description = team.getDescription();
		this.rolelist = rolelist;
	}

	public String getNameen() {
		return nameen;
	}

	public void setNameen(String nameen) {
		this.nameen = nameen;
	}

	public String getNamear() {
		return namear;
	}

	public void setNamear(String namear) {
		this.namear = namear;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Roles> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<Roles> rolelist) {
		this.rolelist = rolelist;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
