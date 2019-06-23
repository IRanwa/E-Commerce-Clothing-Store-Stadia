package com.apiit.stadia.DTOClasses;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class OrderProductsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter OrdersDTO orders;
	
	@Getter @Setter ProductDTO product;
	
	@Getter @Setter RatingDTO rating;
	
	//@EmbeddedId
	//@Getter @Setter OrderProductsIdentity OrderProductsId;
	
	@Getter @Setter int quantity;
	
}

