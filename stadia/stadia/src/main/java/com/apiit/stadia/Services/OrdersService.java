package com.apiit.stadia.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apiit.stadia.DTOClasses.OrderProductsDTO;
import com.apiit.stadia.DTOClasses.OrdersDTO;
import com.apiit.stadia.EnumClasses.OrderStatus;
import com.apiit.stadia.ModelClasses.Login;
import com.apiit.stadia.ModelClasses.OrderProducts;
import com.apiit.stadia.ModelClasses.OrderProductsIdentity;
import com.apiit.stadia.ModelClasses.Orders;
import com.apiit.stadia.ModelClasses.Product;
import com.apiit.stadia.ModelClasses.User;
import com.apiit.stadia.Repositories.LoginRepository;
import com.apiit.stadia.Repositories.OrderProductsRepository;
import com.apiit.stadia.Repositories.OrdersRepository;
import com.apiit.stadia.Repositories.ProductRepository;

@Service
public class OrdersService {
	
	@Autowired
	OrdersRepository ordersRepo;
	@Autowired
	OrderProductsRepository orderProdRepo;
	@Autowired
	ProductRepository prodRepo;
	
	@Autowired
	ModelClassToDTO modelToDTO;
	
	
	public boolean addToCart(long prodId,User user) {
		Orders order = ordersRepo.findByUserAndStatus(user,OrderStatus.Cart);
		if(order==null) {
			order = new Orders(user,OrderStatus.Cart,new Date());
			order = ordersRepo.save(order);
		}
		Optional<Product> product = prodRepo.findById(prodId);
		
		if(product.isPresent()) {
			OrderProducts orderProd = orderProdRepo.findByOrdersAndProduct(order,product.get());
			if(orderProd==null) {
				OrderProductsIdentity orderProdIdentity = new OrderProductsIdentity(order.getId(),product.get().getId());
				orderProd = new OrderProducts(order,product.get(),orderProdIdentity,1);
			}else {
				orderProd.setQuantity(orderProd.getQuantity() + 1);
			}
			orderProdRepo.save(orderProd);
			return true;
		}
		return false;
	}
	
	public ResponseEntity<Object> getCart(User user) {
		Orders order = ordersRepo.findByUserAndStatus(user, OrderStatus.Cart);
		if(order!=null) {
			List<OrderProducts> orderProds = orderProdRepo.findByOrders(order);
			List<OrderProductsDTO> orderProdDTOList = new ArrayList<OrderProductsDTO>();
			for(OrderProducts orderProd : orderProds) {
				orderProdDTOList.add(modelToDTO.orderProductToDTO(orderProd));
			}
			return new ResponseEntity<>(orderProdDTOList,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	public boolean placeOrder(User user) {
		Orders order = ordersRepo.findByUserAndStatus(user, OrderStatus.Cart);
		if(order!=null) {
			order.setStatus(OrderStatus.Pending);
			ordersRepo.save(order);
			return true;
		}
		return false;
	}
	
	public List<OrdersDTO> getOrdersList(User user){
		List<Orders> orders = ordersRepo.findByUser(user);
		List<OrdersDTO> ordersDTOList = new ArrayList<OrdersDTO>();
		for(Orders order : orders) {
			if(order.getStatus()!=OrderStatus.Cart) {
				//Order to OrderDTO class
				OrdersDTO orderDTO = modelToDTO.ordersToDTO(order);
				//OrderProducts to OrderProductsDTO class
				List<OrderProducts> orderProdList = order.getOrderProducts();
				List<OrderProductsDTO> orderProdDTOList = new ArrayList<OrderProductsDTO>();
				for(OrderProducts orderProd : orderProdList) {
					orderProdDTOList.add(modelToDTO.orderProductToDTO(orderProd));
				}
				orderDTO.setOrderProducts(orderProdDTOList);
				ordersDTOList.add(orderDTO);
			}
		}
		return ordersDTOList;
	}

}
