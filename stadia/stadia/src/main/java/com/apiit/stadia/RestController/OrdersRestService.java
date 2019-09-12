package com.apiit.stadia.RestController;

import java.util.List;

import com.apiit.stadia.DTOClasses.OrderProductsDTO;
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
	public ResponseEntity<List<OrderProductsDTO>> getCart(@RequestBody User user) {
		return orderService.getCart(user);
	}

	@PostMapping("/UpdateCartQty")
	public ResponseEntity<Boolean> updateCartQty(@RequestBody OrderProducts orderProducts){
		return orderService.updateCartQty(orderProducts);
	}

	@DeleteMapping("/DeleteCartItem/{orderId}/{prodSizeId}")
	public ResponseEntity<Boolean> deleteCartItem(@PathVariable long orderId, @PathVariable long prodSizeId){
		return orderService.deleteCartItem(orderId,prodSizeId);
	}

	@PostMapping("/DeleteCartItems")
	public ResponseEntity<Boolean> deleteCartItems(@RequestBody Orders order){
		return orderService.deleteCartItems(order);
	}
	
	@PostMapping("/PlaceOrder")
	public ResponseEntity<Boolean> placeOrder(@RequestBody Orders order) {
		return orderService.placeOrder(order);
	}
	
	@PostMapping("/ListOrders")
	public List<OrdersDTO> getOrdersList(@RequestBody  User user){
		return orderService.getOrdersList(user);
	}

}
