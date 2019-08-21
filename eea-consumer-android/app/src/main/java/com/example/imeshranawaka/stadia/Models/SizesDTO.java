package com.example.imeshranawaka.stadia.Models;

import java.io.Serializable;
import java.util.List;


public class SizesDTO implements Serializable{

	List<ProductSizesDTO> productSizes;
	long id;
	String size;
	String description;

	public List<ProductSizesDTO> getProductSizes() {
		return productSizes;
	}

	public void setProductSizes(List<ProductSizesDTO> productSizes) {
		this.productSizes = productSizes;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

