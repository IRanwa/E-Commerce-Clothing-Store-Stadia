package com.apiit.stadia.DTOClasses;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class SizesDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter List<ProductSizesDTO> productSizes;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter long id;
	
	@Getter @Setter String size;
	
	@Getter @Setter String description;
}

