package com.service.auth.builder.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.service.auth.config.SanitizedStringDeserializer;

import jakarta.validation.constraints.NotBlank;

public class TeamRq {

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
    private String code;

	@NotBlank
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
    private String nameen;

	@NotBlank
    @JsonDeserialize(using = SanitizedStringDeserializer.class)
    private String namear;

    @JsonDeserialize(using = SanitizedStringDeserializer.class)
    private String description;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
