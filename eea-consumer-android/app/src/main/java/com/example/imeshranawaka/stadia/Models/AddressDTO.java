package com.example.imeshranawaka.stadia.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AddressDTO implements Serializable{

	List<OrdersDTO> ordersShipping;
	List<OrdersDTO> ordersBilling;
	UserDTO user;
	long id;
	@SerializedName("fname")
	String fName;
	@SerializedName("lname")
	String lName;
	String contactNo;
	String address;
	String city;
	String province;
	String zipCode;
	String country;
	String addType;

	public List<OrdersDTO> getOrdersShipping() {
		return ordersShipping;
	}

	public void setOrdersShipping(List<OrdersDTO> ordersShipping) {
		this.ordersShipping = ordersShipping;
	}

	public List<OrdersDTO> getOrdersBilling() {
		return ordersBilling;
	}

	public void setOrdersBilling(List<OrdersDTO> ordersBilling) {
		this.ordersBilling = ordersBilling;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}
}

