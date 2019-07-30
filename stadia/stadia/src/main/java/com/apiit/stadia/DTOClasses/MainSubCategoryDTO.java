package com.apiit.stadia.DTOClasses;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.apiit.stadia.ModelClasses.MainCategory;
import com.apiit.stadia.ModelClasses.SubCategory;

import lombok.Getter;
import lombok.Setter;

public class MainSubCategoryDTO {
	
	@Getter @Setter long id;
	
	@Getter @Setter MainCategoryDTO mainCategory;
	
	@Getter @Setter SubCategoryDTO subCategory;
}
