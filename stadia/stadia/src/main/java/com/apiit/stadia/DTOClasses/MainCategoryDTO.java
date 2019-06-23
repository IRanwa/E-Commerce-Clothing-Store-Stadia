package com.apiit.stadia.DTOClasses;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.apiit.stadia.EnumClasses.Gender;

import lombok.Getter;
import lombok.Setter;

public class MainCategoryDTO {
	@Getter @Setter int id;
	
	@Getter @Setter String mainCatTitle;
	
	@Getter @Setter String mainCatDesc;
	
	@Getter @Setter String mainCatImg;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter Gender type;
	
	@Getter @Setter List<SubCategoryDTO> subCategoryDTO;
}
