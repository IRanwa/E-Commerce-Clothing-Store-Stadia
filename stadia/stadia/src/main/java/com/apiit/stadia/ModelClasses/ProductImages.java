package com.apiit.stadia.ModelClasses;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class ProductImages {
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="prodId",referencedColumnName="id")
	@Getter @Setter Product product;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter long id;
	
	@Getter @Setter String path;

}
