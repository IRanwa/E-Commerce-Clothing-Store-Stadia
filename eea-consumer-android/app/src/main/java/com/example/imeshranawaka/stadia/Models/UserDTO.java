package com.example.imeshranawaka.stadia.Models;

import com.example.imeshranawaka.stadia.EnumClasses.Gender;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class UserDTO implements Serializable{

	private LoginDTO login;
	private List<AddressDTO> address;
	private List<OrdersDTO> orders;
	List<RatingDTO> rating;
	private String email;
	@SerializedName("dob")
	private Date dob;
	private Gender gender;
	private String contactNo;
	
	public UserDTO() {
		
	}

	public LoginDTO getLogin() {
		return login;
	}

	public void setLogin(LoginDTO login) {
		this.login = login;
	}

	public List<AddressDTO> getAddress() {
		return address;
	}

	public void setAddress(List<AddressDTO> address) {
		this.address = address;
	}

	public List<OrdersDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrdersDTO> orders) {
		this.orders = orders;
	}

	public List<RatingDTO> getRating() {
		return rating;
	}

	public void setRating(List<RatingDTO> rating) {
		this.rating = rating;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
}
