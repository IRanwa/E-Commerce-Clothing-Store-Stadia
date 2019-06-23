package com.apiit.stadia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiit.stadia.ModelClasses.OrderProducts;
import com.apiit.stadia.ModelClasses.OrderProductsIdentity;
import com.apiit.stadia.ModelClasses.Orders;
import com.apiit.stadia.ModelClasses.Product;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, OrderProductsIdentity>{
	//Retrieve specific order product (Cart specific product)
	public OrderProducts findByOrdersAndProduct(Orders orders,Product product);
	
	//Retrieve all products related to specific order
	public List<OrderProducts> findByOrders(Orders orders);
}
