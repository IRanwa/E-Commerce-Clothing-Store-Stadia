package com.example.imeshranawaka.stadia.Models;

import java.io.Serializable;
import java.util.Date;

public class RatingDTO implements Serializable{

	UserDTO user;
	OrderProductsDTO orderProducts;
	long id;
	int rating;
	String comment;
	Date ratedDate;

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public OrderProductsDTO getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(OrderProductsDTO orderProducts) {
		this.orderProducts = orderProducts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getRatedDate() {
		return ratedDate;
	}

	public void setRatedDate(Date ratedDate) {
		this.ratedDate = ratedDate;
	}
}
