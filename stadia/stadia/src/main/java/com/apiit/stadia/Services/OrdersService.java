package com.apiit.stadia.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apiit.stadia.DTOClasses.ProductDTO;
import com.apiit.stadia.EnumClasses.PaymentMethod;
import com.apiit.stadia.ModelClasses.*;
import com.apiit.stadia.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apiit.stadia.DTOClasses.OrderProductsDTO;
import com.apiit.stadia.DTOClasses.OrdersDTO;
import com.apiit.stadia.EnumClasses.OrderStatus;

@Service
public class OrdersService {
	
	@Autowired
	OrdersRepository ordersRepo;
	@Autowired
	OrderProductsRepository orderProdRepo;
	@Autowired
	ProductSizesRepository prodSizesRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	AddressRepository addressRepo;
	
	@Autowired
	ModelClassToDTO modelToDTO;


	public boolean addToCart(OrderProducts orderProducts) {
		long prodSizesId = orderProducts.getProductSizes().getId();
		String userEmail = orderProducts.getOrders().getUser().getEmail();
		int qty = orderProducts.getProductSizes().getQuantity();

		Optional<User> userOptional = userRepo.findById(userEmail);
		if(userOptional.isPresent()) {
			User user = userOptional.get();
			Orders order = ordersRepo.findByUserAndStatus(user, OrderStatus.Cart);
			if (order == null) {
				order = new Orders(user, OrderStatus.Cart, new Date());
				order = ordersRepo.save(order);
			}
			Optional<ProductSizes> productSizes = prodSizesRepo.findById(prodSizesId);

			if (productSizes.isPresent()) {
				OrderProducts orderProd = orderProdRepo.findByOrdersAndProductSizes(order, productSizes.get());
				if (orderProd == null) {
					OrderProductsIdentity orderProdIdentity = new OrderProductsIdentity(order.getId(), productSizes.get().getId());
					orderProd = new OrderProducts(order, productSizes.get(), orderProdIdentity, qty);
				} else {
					orderProd.setQuantity(qty);
				}
				orderProdRepo.save(orderProd);
				return true;
			}
		}
		return false;
	}
	
	public ResponseEntity<List<OrderProductsDTO>> getCart(User user) {
		List<OrderProductsDTO> orderProdDTOList = new ArrayList<>();
		Optional<User> userOptional = userRepo.findById(user.getEmail());
		if(userOptional.isPresent()) {
			user = userOptional.get();
			Orders order = ordersRepo.findByUserAndStatus(user, OrderStatus.Cart);
			if (order != null) {
				List<OrderProducts> orderProds = orderProdRepo.findByOrders(order);

				for (OrderProducts orderProd : orderProds) {
					ProductSizes prodSize = orderProd.getProductSizes();
					if (orderProd.getQuantity()>prodSize.getQuantity()) {
						if(prodSize.getQuantity()!=0 ){
							orderProd.setQuantity(1);
						}else{
							orderProd.setQuantity(0);
						}
					}
					Product product = prodSize.getProduct();
					OrderProductsDTO orderProdDTO = modelToDTO.orderProductToDTO(orderProd,product);
					orderProdDTOList.add(orderProdDTO);
				}
				return new ResponseEntity<>(orderProdDTOList, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(orderProdDTOList, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<Boolean> updateCartQty(OrderProducts orderProducts){
		Orders orders = orderProducts.getOrders();
		Optional<Orders> ordersOptional = ordersRepo.findById(orders.getId());
		if(ordersOptional.isPresent()){
			ProductSizes prodSizes = orderProducts.getProductSizes();
			Optional<ProductSizes> prodSizesOptional = prodSizesRepo.findById(prodSizes.getId());
			if(prodSizesOptional.isPresent()){
				OrderProducts orderProd = orderProdRepo.findByOrdersAndProductSizes(ordersOptional.get(), prodSizesOptional.get());
				orderProd.setQuantity(orderProducts.getQuantity());
				orderProdRepo.save(orderProd);
				return new ResponseEntity<>(true,HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<Boolean> deleteCartItem(long orderId,long prodSizeId){
		Optional<Orders> orderOptional = ordersRepo.findById(orderId);
		if(orderOptional.isPresent()){
			Optional<ProductSizes> prodSizeOptional = prodSizesRepo.findById(prodSizeId);
			if(prodSizeOptional.isPresent()){
				OrderProducts orderProd = orderProdRepo.findByOrdersAndProductSizes(orderOptional.get(), prodSizeOptional.get());

				Orders order = orderProd.getOrders();

				orderProdRepo.delete(orderProd);
				return new ResponseEntity<>(true,HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<Boolean> deleteCartItems(Orders removeOrder){
		boolean status = true;
		long orderId = removeOrder.getId();
		Optional<Orders> orderOptional = ordersRepo.findById(orderId);
		if(orderOptional.isPresent()){
			for(OrderProducts orderProd : removeOrder.getOrderProducts()){
				long prodSizeId = orderProd.getProductSizes().getId();
				Optional<ProductSizes> prodSizeOptional = prodSizesRepo.findById(prodSizeId);
				if(prodSizeOptional.isPresent()){
					orderProd = orderProdRepo.findByOrdersAndProductSizes(orderOptional.get(), prodSizeOptional.get());

					if(orderProd!=null) {
						orderProdRepo.delete(orderProd);
					}else{
						status = false;
						break;
					}
				}else{
					status = false;
					break;
				}
			}
		}else{
			status = false;
		}
		if(status){
			return new ResponseEntity<>(true,HttpStatus.OK);
		}
		return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	public ResponseEntity<Boolean> placeOrder(Orders newOrders) {
		Optional<User> userOptional = userRepo.findById(newOrders.getUser().getEmail());
		if(userOptional.isPresent()){
			Orders order = ordersRepo.findByUserAndStatus(userOptional.get(), OrderStatus.Cart);
			if(order!=null) {
				if(newOrders.getOrderProducts().size()>0){

				}else{
					List<OrderProducts> orderProdList = order.getOrderProducts();
					for(OrderProducts orderProd : orderProdList){
						ProductSizes productSize = orderProd.getProductSizes();
						productSize.setQuantity(productSize.getQuantity()-orderProd.getQuantity());
						prodSizesRepo.save(productSize);
					}
					order.setStatus(OrderStatus.Pending);
					Optional<Address> billingAddress = addressRepo.findById(newOrders.getBillingAddress().getId());
					Optional<Address> shippingAddress = addressRepo.findById(newOrders.getShippingAddress().getId());
					if(billingAddress.isPresent() && shippingAddress.isPresent()){
						order.setBillingAddress(billingAddress.get());
						order.setShippingAddress(shippingAddress.get());
						order.setPaymentMethod(newOrders.getPaymentMethod());

						ordersRepo.save(order);
					}

				}
				return new ResponseEntity<>(true,HttpStatus.OK);
			}
			return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
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
					orderProdDTOList.add(modelToDTO.orderProductToDTO(orderProd,null));
				}
				orderDTO.setOrderProducts(orderProdDTOList);
				ordersDTOList.add(orderDTO);
			}
		}
		return ordersDTOList;
	}


}
