package com.apiit.stadia.Repositories;

import java.util.List;

import com.apiit.stadia.ModelClasses.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProducts, OrderProductsIdentity>{
	//Retrieve specific order product (Cart specific product)
	public OrderProducts findByOrdersAndProductSizes(Orders orders, ProductSizes productSizes);
	
	//Retrieve all products related to specific order
	public List<OrderProducts> findByOrders(Orders orders);
}
