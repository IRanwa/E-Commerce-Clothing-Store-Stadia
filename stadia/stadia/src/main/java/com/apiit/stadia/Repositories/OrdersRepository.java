package com.apiit.stadia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiit.stadia.EnumClasses.OrderStatus;
import com.apiit.stadia.ModelClasses.Orders;
import com.apiit.stadia.ModelClasses.User;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
	//Retrieve cart order for specific user
	public Orders findByUserAndStatus(User user, OrderStatus status);
	
	//Retrieve all orders for specific user
	public List<Orders> findByUser(User user);
}
