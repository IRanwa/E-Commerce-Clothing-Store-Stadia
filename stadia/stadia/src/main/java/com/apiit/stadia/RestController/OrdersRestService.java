package com.apiit.stadia.RestController;

import java.util.List;

import com.apiit.stadia.ModelClasses.OrderProducts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiit.stadia.DTOClasses.OrdersDTO;
import com.apiit.stadia.ModelClasses.Login;
import com.apiit.stadia.ModelClasses.Orders;
import com.apiit.stadia.ModelClasses.User;
import com.apiit.stadia.Services.OrdersService;

@CrossOrigin("*")
@RestController
public class OrdersRestService {
	@Autowired
	OrdersService orderService;
	
	@PostMapping("/AddToCart")
	public String addToCart(@RequestBody OrderProducts orderProducts) {
		return String.valueOf(orderService.addToCart(orderProducts));
		//return "success";
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
