package com.service.auth.model;
import com.service.auth.builder.request.TeamRq;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "teams")
public class Teams {

    @Id
    @Column(name = "code", nullable = false, length = 200)
    private String code;
    
    @NotNull
    @Column(name = "nameen", nullable = false, length = 200)
    private String nameen;

    @Column(name = "namear", nullable = false, length = 200)
    private String namear;

    @Lob
    @Column(name = "description")
    private String description;
    
    public Teams(String code, @NotNull String nameen, String namear) {
		super();
		this.code = code;
		this.nameen = nameen;
		this.namear = namear;
	}
	public Teams() {}
    public Teams(@Valid TeamRq req) {

    	this.code = req.getCode() == null ? req.getNameen().toLowerCase() : req.getCode();
        this.nameen = req.getNameen();
        this.namear = req.getNamear();
        this.description = req.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
