package com.apiit.stadia.ModelClasses;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
public class OrderProductsIdentity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Getter @Setter long orderId;

	@Getter @Setter long prodId;
	
	public OrderProductsIdentity() {}

	public OrderProductsIdentity(long orderId, long prodId) {
		super();
		this.orderId = orderId;
		this.prodId = prodId;
	}
	
	
}
