package com.example.imeshranawaka.stadia.Models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProductDTO implements Serializable{
	List<ProductSizesDTO> productSizes;
	List<ProductImagesDTO> productImages;
	MainSubCategoryDTO mainSubCategory;
	long id;
	String title;
	String description;
	double price;
	Date createdDate;
	Date modifyDate;
	String sortBy;

	public List<ProductSizesDTO> getProductSizes() {
		return productSizes;
	}

	public void setProductSizes(List<ProductSizesDTO> productSizes) {
		this.productSizes = productSizes;
	}

	public List<ProductImagesDTO> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImagesDTO> productImages) {
		this.productImages = productImages;
	}

	public MainSubCategoryDTO getMainSubCategory() {
		return mainSubCategory;
	}

	public void setMainSubCategory(MainSubCategoryDTO mainSubCategory) {
		this.mainSubCategory = mainSubCategory;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
}

