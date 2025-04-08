package com.service.auth.builder.response;

import java.util.List;

import com.service.auth.model.Teams;

public class TeamsRolesUsersResponse {

    private String code;
    
    private String nameen;

    private String namear;
    
    private List<RolesUsersResponse> rolesUsersResponses;

	public TeamsRolesUsersResponse() {
		super();
	}

	public TeamsRolesUsersResponse(Teams team,
			List<RolesUsersResponse> rolesUsersResponses) {
		super();
		this.code = team.getCode();
		this.nameen = team.getNameen();
		this.namear = team.getNamear();
		this.rolesUsersResponses = rolesUsersResponses;
	}

	public List<RolesUsersResponse> getRolesUsersResponses() {
		return rolesUsersResponses;
	}

	public void setRolesUsersResponses(List<RolesUsersResponse> rolesUsersResponses) {
		this.rolesUsersResponses = rolesUsersResponses;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
}
