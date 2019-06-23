package com.apiit.stadia.DTOClasses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.apiit.stadia.EnumClasses.OrderStatus;
import com.apiit.stadia.EnumClasses.PaymentMethod;

import lombok.Getter;
import lombok.Setter;

@Entity
public class OrdersDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter @Setter UserDTO user;
	
	@Getter @Setter AddressDTO shippingAddress;
	
	@Getter @Setter AddressDTO billingAddress;
	
	@Getter @Setter List<OrderProductsDTO> orderProducts;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter @Setter long id;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter private OrderStatus status;
	
	@Getter @Setter Date purchasedDate;
	
	@Getter @Setter Date deliverDate;
	
	@Getter @Setter Date orderCompleteDate;
	
	@Enumerated(EnumType.STRING)
	@Getter @Setter PaymentMethod paymentMethod;

}

