package com.apiit.stadia.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiit.stadia.DTOClasses.OrdersDTO;
import com.apiit.stadia.ModelClasses.Login;
import com.apiit.stadia.ModelClasses.Orders;
import com.apiit.stadia.ModelClasses.User;
import com.apiit.stadia.Services.OrdersService;

@RestController
public class OrdersRestService {
	@Autowired
	OrdersService orderService;
	
	@PostMapping("/Cart/{prodId}")
	public String addToCart(@PathVariable long prodId, User user) {
		return "{Status:"+orderService.addToCart(prodId, user)+"}";
	}

	@PostMapping("/ViewCart")
	public ResponseEntity<Object> getCart(User user) {
		return orderService.getCart(user);
	}
	
	@PostMapping("/PlaceOrder")
	public String placeOrder(User user) {
		return "{Status:"+orderService.placeOrder(user)+"}";
	}
	
	@PostMapping("/ListOrders")
	public List<OrdersDTO> getOrdersList(User user){
		return orderService.getOrdersList(user);
	}
}
