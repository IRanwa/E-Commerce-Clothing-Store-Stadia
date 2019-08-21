package com.example.imeshranawaka.stadia.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class OrdersDTO implements Serializable{
	UserDTO user;
	AddressDTO shippingAddress;
	AddressDTO billingAddress;
	List<OrderProductsDTO> orderProducts;
	long id;
	private String status;
	Date purchasedDate;
	Date deliverDate;
	Date orderCompleteDate;
	String paymentMethod;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public AddressDTO getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(AddressDTO shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public AddressDTO getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressDTO billingAddress) {
		this.billingAddress = billingAddress;
	}

	public List<OrderProductsDTO> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProductsDTO> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(Date purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public Date getOrderCompleteDate() {
		return orderCompleteDate;
	}

	public void setOrderCompleteDate(Date orderCompleteDate) {
		this.orderCompleteDate = orderCompleteDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}

