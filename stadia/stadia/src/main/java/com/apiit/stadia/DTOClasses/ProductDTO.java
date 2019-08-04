package com.apiit.stadia.DTOClasses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.apiit.stadia.EnumClasses.SortBy;
import lombok.Getter;
import lombok.Setter;

@Entity
public class ProductDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter List<ProductSizesDTO> productSizes;
	
	@Getter @Setter List<ProductImagesDTO> productImages;
	
	@Getter @Setter MainSubCategoryDTO mainSubCategory;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter long id;
	
	@Getter @Setter String title;
	
	@Getter @Setter String description;
	
	@Getter @Setter double price;
	
	@Getter @Setter Date createdDate;
	
	@Getter @Setter Date modifyDate;

	@Getter @Setter SortBy sortBy;
}

