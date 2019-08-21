package com.example.imeshranawaka.stadia.Models;

import java.util.List;

public class MainCategoryDTO {
	int id;
	String mainCatTitle;
	String mainCatDesc;
	String mainCatImg;
	String mainCatImgName;
	String type;
	List<SubCategoryDTO> subCategoryDTO;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMainCatTitle() {
		return mainCatTitle;
	}

	public void setMainCatTitle(String mainCatTitle) {
		this.mainCatTitle = mainCatTitle;
	}

	public String getMainCatDesc() {
		return mainCatDesc;
	}

	public void setMainCatDesc(String mainCatDesc) {
		this.mainCatDesc = mainCatDesc;
	}

	public String getMainCatImg() {
		return mainCatImg;
	}

	public void setMainCatImg(String mainCatImg) {
		this.mainCatImg = mainCatImg;
	}

	public String getMainCatImgName() {
		return mainCatImgName;
	}

	public void setMainCatImgName(String mainCatImgName) {
		this.mainCatImgName = mainCatImgName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SubCategoryDTO> getSubCategoryDTO() {
		return subCategoryDTO;
	}

	public void setSubCategoryDTO(List<SubCategoryDTO> subCategoryDTO) {
		this.subCategoryDTO = subCategoryDTO;
	}
}
