package com.apiit.stadia.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.apiit.stadia.DTOClasses.*;
import com.apiit.stadia.ModelClasses.*;
import org.springframework.stereotype.Service;

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

		List<ProductImages> prodImages = product.getProductImages();
		List<ProductImagesDTO> prodImagesDTO = new ArrayList<>();
		for(ProductImages prodImage : prodImages){
			prodImagesDTO.add(productImagesToDTO(prodImage,product.getId()));
		}
		productDTO.setProductImages(prodImagesDTO);

		List<ProductSizes> prodSizes = product.getProductSizes();
		List<ProductSizesDTO> prodSizesDTO = new ArrayList<>();
		for(ProductSizes prodSize : prodSizes){
			prodSizesDTO.add(productSizesToDTO(prodSize));
		}
		productDTO.setProductSizes(prodSizesDTO);

		MainSubCategoryDTO mainSubCatDTO = mainSubCategoryToDTO(product.getMainSubCategory());
		productDTO.setMainSubCategory(mainSubCatDTO);
		return productDTO;
	}
	
	public ProductImagesDTO productImagesToDTO(ProductImages prodImage,long id) {
		ProductImagesDTO prodImageDTO = new ProductImagesDTO();
		prodImageDTO.setId(prodImage.getId());
		prodImageDTO.setFilename(prodImage.getPath());
		String path = System.getProperty("user.dir")+"/Images/Products/"+id+"/"+prodImage.getPath();
		File file = new File(path);
		if(file.exists()) {
			try {
				FileInputStream in = new FileInputStream(file);
				byte[] imageData = new byte[(int) file.length()];
				in.read(imageData);
				in.close();

				String base64Image = Base64.getEncoder().encodeToString(imageData);
				prodImageDTO.setPath("data:image/png;base64, "+base64Image);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prodImageDTO;
	}
	
	public ProductSizesDTO productSizesToDTO(ProductSizes prodSize) {
		ProductSizesDTO prodSizeDTO = new ProductSizesDTO();
		prodSizeDTO.setId(prodSize.getId());
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
	
	public OrderProductsDTO orderProductToDTO(OrderProducts orderProduct,Product product) {
		OrderProductsDTO orderProdDTO = new OrderProductsDTO();

		//Set Product and Product Sizes
		ProductSizesDTO productSizeDTO;
		if(product!=null){
			ProductDTO productDTO = productToDTO(product);
			productSizeDTO = productSizesToDTO(orderProduct.getProductSizes());
			productSizeDTO.setProduct(productDTO);
		}else{
			productSizeDTO = productSizesToDTO(orderProduct.getProductSizes());
		}
		orderProdDTO.setProductSizes(productSizeDTO);

		//Set Quantity
		orderProdDTO.setQuantity(orderProduct.getQuantity());


		//Set Orders
		Orders orders = orderProduct.getOrders();
		orderProdDTO.setOrders(ordersToDTO(orders));

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

	public MainSubCategoryDTO mainSubCategoryToDTO(MainSubCategory mainSubCategory) {
		MainSubCategoryDTO mainSubCatDTO = new MainSubCategoryDTO();
		mainSubCatDTO.setId(mainSubCategory.getId());

		mainSubCatDTO.setSubCategory(subCategoryToDTO(mainSubCategory.getSubCategory()));
		mainSubCatDTO.setMainCategory(mainCategoryToDTO(mainSubCategory.getMainCategory()));
		return mainSubCatDTO;
	}

	public UserDTO userToDTO(User user){
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(user.getEmail());
		userDTO.setContactNo(user.getContactNo());
		userDTO.setDob(user.getDob());
		userDTO.setGender(user.getGender());

		userDTO.setLogin(loginToDTO(user.getLogin()));
		return userDTO;
	}

	public LoginDTO loginToDTO(Login login){
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setEmail(login.getEmail());
		loginDTO.setFName(login.getFName());
		loginDTO.setLName(login.getLName());
		loginDTO.setLastLogin(login.getLastLogin());
		loginDTO.setRole(login.getRole());
		return loginDTO;
	}

	public AddressDTO addressToDTO(Address address){
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(address.getId());
		addressDTO.setFName(address.getFName());
		addressDTO.setLName(address.getLName());
		addressDTO.setContactNo(address.getContactNo());
		addressDTO.setAddress(address.getAddress());
		addressDTO.setCity(address.getCity());
		addressDTO.setProvince(address.getProvince());
		addressDTO.setZipCode(address.getZipCode());
		addressDTO.setCountry(address.getCountry());
		addressDTO.setAddType(address.getAddType());

		return addressDTO;
	}

	public RatingDTO ratingToDTO(Rating rating){
		RatingDTO ratingDTO = new RatingDTO();
		ratingDTO.setId(rating.getId());
		ratingDTO.setComment(rating.getComment());
		ratingDTO.setRatedDate(rating.getRatedDate());
		ratingDTO.setRating(rating.getRating());
		return ratingDTO;
	}
}
