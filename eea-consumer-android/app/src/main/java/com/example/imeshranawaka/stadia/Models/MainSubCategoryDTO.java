package com.example.imeshranawaka.stadia.Models;


public class MainSubCategoryDTO {
	long id;
	MainCategoryDTO mainCategory;
	SubCategoryDTO subCategory;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MainCategoryDTO getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(MainCategoryDTO mainCategory) {
		this.mainCategory = mainCategory;
	}

	public SubCategoryDTO getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(SubCategoryDTO subCategory) {
		this.subCategory = subCategory;
	}
}
