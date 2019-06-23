package com.apiit.stadia.DTOClasses;

import java.util.List;

import com.apiit.stadia.ModelClasses.MainCategory;

import lombok.Getter;
import lombok.Setter;

public class SubCategoryDTO {
	@Getter @Setter int id;
	
	@Getter @Setter String subCatTitle;
	
	@Getter @Setter String subCatDesc;
	
	@Getter @Setter String subCatImg;
	
	@Getter @Setter String subCatImgName;
}
