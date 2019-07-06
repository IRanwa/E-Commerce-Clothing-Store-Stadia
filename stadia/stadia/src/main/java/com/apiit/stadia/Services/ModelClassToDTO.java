package com.apiit.stadia.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.apiit.stadia.DTOClasses.MainCategoryDTO;
import com.apiit.stadia.DTOClasses.OrderProductsDTO;
import com.apiit.stadia.DTOClasses.OrdersDTO;
import com.apiit.stadia.DTOClasses.ProductDTO;
import com.apiit.stadia.DTOClasses.ProductImagesDTO;
import com.apiit.stadia.DTOClasses.ProductSizesDTO;
import com.apiit.stadia.DTOClasses.RatingDTO;
import com.apiit.stadia.DTOClasses.SizesDTO;
import com.apiit.stadia.DTOClasses.SubCategoryDTO;
import com.apiit.stadia.DTOClasses.UserDTO;
import com.apiit.stadia.ModelClasses.MainCategory;
import com.apiit.stadia.ModelClasses.OrderProducts;
import com.apiit.stadia.ModelClasses.Orders;
import com.apiit.stadia.ModelClasses.Product;
import com.apiit.stadia.ModelClasses.ProductImages;
import com.apiit.stadia.ModelClasses.ProductSizes;
import com.apiit.stadia.ModelClasses.Rating;
import com.apiit.stadia.ModelClasses.Sizes;
import com.apiit.stadia.ModelClasses.SubCategory;
import com.apiit.stadia.ModelClasses.User;

@Service
public class ModelClassToDTO {

	public ProductDTO productToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setTitle(product.getTitle());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		productDTO.setCreatedDate(product.getCreatedDate());
		productDTO.setModifyDate(product.getModifyDate());
		return productDTO;
	}
	
	public ProductImagesDTO productImagesToDTO(ProductImages prodImage) {
		ProductImagesDTO prodImageDTO = new ProductImagesDTO();
		prodImageDTO.setId(prodImage.getId());
		prodImageDTO.setPath(prodImage.getPath());
		return prodImageDTO;
	}
	
	public ProductSizesDTO productSizesToDTO(ProductSizes prodSize) {
		ProductSizesDTO prodSizeDTO = new ProductSizesDTO();
		prodSizeDTO.setQuantity(prodSize.getQuantity());
		prodSizeDTO.setSizes(sizesToDTO(prodSize.getSizes()));
		return prodSizeDTO;
	}
	
	public SizesDTO sizesToDTO(Sizes size) {
		SizesDTO sizeDTO = new SizesDTO();
		sizeDTO.setId(size.getId());
		sizeDTO.setSize(size.getSize());
		sizeDTO.setDescription(size.getDescription());
		return sizeDTO;
	}
	
	public OrderProductsDTO orderProductToDTO(OrderProducts orderProduct) {
		OrderProductsDTO orderProdDTO = new OrderProductsDTO();
		orderProdDTO.setProduct(productToDTO(orderProduct.getProduct()));
		orderProdDTO.setQuantity(orderProduct.getQuantity());
		return orderProdDTO;
	}
	
	public OrdersDTO ordersToDTO(Orders order) {
		OrdersDTO orderDTO = new OrdersDTO();
		orderDTO.setId(order.getId());
		orderDTO.setStatus(order.getStatus());
		orderDTO.setPurchasedDate(order.getPurchasedDate());
		orderDTO.setDeliverDate(order.getDeliverDate());
		orderDTO.setOrderCompleteDate(order.getOrderCompleteDate());
		orderDTO.setPaymentMethod(order.getPaymentMethod());
		return orderDTO;
	}
	
	public MainCategoryDTO mainCategoryToDTO(MainCategory mainCat) {
		MainCategoryDTO mainCatDTO = new MainCategoryDTO();
		mainCatDTO.setId(mainCat.getId());
		mainCatDTO.setMainCatTitle(mainCat.getMainCatTitle());
		mainCatDTO.setMainCatDesc(mainCat.getMainCatDesc());
		mainCatDTO.setType(mainCat.getType());
		mainCatDTO.setMainCatImgName(mainCat.getMainCatImg());
		String path = System.getProperty("user.dir")+"/Images/MainCategory/"+mainCat.getMainCatImg();
		File file = new File(path);
		if(file.exists()) {
			try {
				FileInputStream in = new FileInputStream(file);
				byte[] imageData = new byte[(int) file.length()];
				in.read(imageData);
				in.close();

				String base64Image = Base64.getEncoder().encodeToString(imageData);
				mainCatDTO.setMainCatImg("data:image/png;base64, "+base64Image);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		return mainCatDTO;
	}
	
	public SubCategoryDTO subCategoryToDTO(SubCategory subCat) {
		SubCategoryDTO subCatDTO = new SubCategoryDTO();
		subCatDTO.setId(subCat.getId());
		subCatDTO.setSubCatTitle(subCat.getSubCatTitle());
		subCatDTO.setSubCatDesc(subCat.getSubCatDesc());
		subCatDTO.setSubCatImgName(subCat.getSubCatImg());
		String path = System.getProperty("user.dir")+"/Images/SubCategory/"+subCat.getSubCatImg();
		File file = new File(path);
		if(file.exists()) {
			try {
				FileInputStream in = new FileInputStream(file);
				byte[] imageData = new byte[(int) file.length()];
				in.read(imageData);
				in.close();

				String base64Image = Base64.getEncoder().encodeToString(imageData);
				subCatDTO.setSubCatImg("data:image/png;base64, "+base64Image);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		return subCatDTO;
	}
}
