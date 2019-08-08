package com.apiit.stadia.ModelClasses;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
public class ProductSizes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter @Setter long id;
	
	@ManyToOne
	@JoinColumn(name="prodId",referencedColumnName="id")
	@Getter @Setter Product product;
	
	@ManyToOne
	@JoinColumn(name="sizeId",referencedColumnName="id")
	@Getter @Setter Sizes sizes;

	@OneToMany(mappedBy="productSizes",cascade = CascadeType.REMOVE)
	@Getter @Setter
	List<OrderProducts> orderProducts;
	
	@Getter @Setter int quantity;
}
