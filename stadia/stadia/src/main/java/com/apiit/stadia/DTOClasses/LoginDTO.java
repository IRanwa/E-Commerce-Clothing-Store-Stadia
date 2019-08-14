package com.apiit.stadia.DTOClasses;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.apiit.stadia.EnumClasses.UserRole;

import lombok.Getter;
import lombok.Setter;

@Entity
public class LoginDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Getter @Setter private String email;
	
	@Getter @Setter private String pass;
	
	@Getter @Setter private String fName;
	
	@Getter @Setter private String lName;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter private UserRole role;
	
	@Getter @Setter private Date lastLogin;

	@Getter @Setter String jwttoken;

	public LoginDTO() {
		
	}

	public LoginDTO(String jwttoken, String fName, String lName) {
		this.jwttoken = jwttoken;
		this.fName = fName;
		this.lName = lName;
	}
}
