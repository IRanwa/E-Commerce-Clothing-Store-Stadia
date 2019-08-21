package com.example.imeshranawaka.stadia.Models;

import java.io.Serializable;
import java.util.List;

public class ProductSizesDTO implements Serializable{

	long id;
	ProductDTO product;
	SizesDTO sizes;
	int quantity;
	List<OrderProductsDTO> orderProducts;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public SizesDTO getSizes() {
		return sizes;
	}

	public void setSizes(SizesDTO sizes) {
		this.sizes = sizes;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<OrderProductsDTO> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProductsDTO> orderProducts) {
		this.orderProducts = orderProducts;
	}
}

