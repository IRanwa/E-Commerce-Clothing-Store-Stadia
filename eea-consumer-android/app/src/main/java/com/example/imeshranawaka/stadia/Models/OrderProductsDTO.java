package com.example.imeshranawaka.stadia.Models;

import java.io.Serializable;

public class OrderProductsDTO implements Serializable{
	OrdersDTO orders;
	ProductSizesDTO productSizes;
	RatingDTO rating;
	int quantity;

	public OrdersDTO getOrders() {
		return orders;
	}

	public void setOrders(OrdersDTO orders) {
		this.orders = orders;
	}

	public ProductSizesDTO getProductSizes() {
		return productSizes;
	}

	public void setProductSizes(ProductSizesDTO productSizes) {
		this.productSizes = productSizes;
	}

	public RatingDTO getRating() {
		return rating;
	}

	public void setRating(RatingDTO rating) {
		this.rating = rating;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

