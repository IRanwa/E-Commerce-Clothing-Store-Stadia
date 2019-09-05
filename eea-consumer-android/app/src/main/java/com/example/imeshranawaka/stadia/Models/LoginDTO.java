package com.example.imeshranawaka.stadia.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class LoginDTO implements Serializable {
	
    private String email;
	private String pass;
	@SerializedName("fname")
	private String fName;
	@SerializedName("lname")
	private String lName;
	private String role;
	private Date lastLogin;
    private String jwttoken;

	public LoginDTO() {
		
	}

	public LoginDTO(String email, String pass, String jwttoken) {
		this.email = email;
		this.pass = pass;
		this.jwttoken = jwttoken;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}
}
