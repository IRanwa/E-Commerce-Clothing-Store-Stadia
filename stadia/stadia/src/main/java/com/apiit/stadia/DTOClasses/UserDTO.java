package com.apiit.stadia.DTOClasses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.apiit.stadia.EnumClasses.Gender;
import com.apiit.stadia.ModelClasses.Address;
import com.apiit.stadia.ModelClasses.Login;
import com.apiit.stadia.ModelClasses.Orders;
import com.apiit.stadia.ModelClasses.Rating;

import lombok.Getter;
import lombok.Setter;

@Entity
public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter private LoginDTO login;
	
	@Getter @Setter private List<AddressDTO> address;
	
	@Getter @Setter private List<Orders> orders;
	
	@Getter @Setter private List<Rating> rating;
	
	@Id
	@Getter @Setter private String email;
	
	@Getter @Setter private Date dob;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter private Gender gender;
	
	@Getter @Setter private String contactNo;
	
	public UserDTO() {
		
	}
	
	
}
