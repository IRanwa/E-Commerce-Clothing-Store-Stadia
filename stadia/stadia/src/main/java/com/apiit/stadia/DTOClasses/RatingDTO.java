package com.apiit.stadia.DTOClasses;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class RatingDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter UserDTO user;
	
	@Getter @Setter OrderProductsDTO orderProducts;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter long id;
	
	@Getter @Setter int rating;
	
	@Getter @Setter String comment;
	
	@Getter @Setter Date ratedDate;
}
