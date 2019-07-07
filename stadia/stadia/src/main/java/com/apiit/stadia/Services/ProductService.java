package com.apiit.stadia.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apiit.stadia.DTOClasses.*;
import com.apiit.stadia.ModelClasses.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apiit.stadia.Repositories.MainCategoryRepository;
import com.apiit.stadia.Repositories.MainSubCategoryRepository;
import com.apiit.stadia.Repositories.ProductImagesRepository;
import com.apiit.stadia.Repositories.ProductRepository;
import com.apiit.stadia.Repositories.ProductSizesRepository;
import com.apiit.stadia.Repositories.SizesRepository;

@Service
public class ProductService {
	//Repositories
	@Autowired
	SizesRepository sizesRepo;
	@Autowired
	ProductRepository productRepo;
	@Autowired
	ProductSizesRepository prodSizesRepo;
	@Autowired
	ProductImagesRepository prodImagesRepo;
	@Autowired
	MainSubCategoryRepository mainSubCatRepo;
	@Autowired
	MainCategoryRepository mainCatRepo;
	
	//Model Class to DTO
	@Autowired
	ModelClassToDTO modelToDTO;

	private final int PAGE_COUNT = 2;
	
	//Sizes
	public double getSizesPages() {
		return Math.ceil(Double.valueOf(sizesRepo.count())/PAGE_COUNT);
	}

	public List<SizesDTO> getSizesList(int pageNo){

		List<SizesDTO> newSizesList = new ArrayList<>();
		if(pageNo>=0) {
			Pageable pages = PageRequest.of(pageNo, PAGE_COUNT);

			Page<Sizes> sizesList = sizesRepo.findAll(pages);
			for(Sizes size : sizesList) {
				newSizesList.add(modelToDTO.sizesToDTO(size));
			}
		}else{
			List<Sizes> sizesList = sizesRepo.findAll();
			for(Sizes size : sizesList) {
				newSizesList.add(modelToDTO.sizesToDTO(size));
			}
		}
		return newSizesList;
	}

	public ResponseEntity<SizesDTO> getSize(long id){
		Optional<Sizes> sizeOptional = sizesRepo.findById(id);
		if(sizeOptional.isPresent()){
			return new ResponseEntity<>(modelToDTO.sizesToDTO(sizeOptional.get()),HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<SizesDTO> addSize(Sizes size) {
		size =  sizesRepo.save(size);
		if(size.getId()>0) {
			return new ResponseEntity<SizesDTO>(modelToDTO.sizesToDTO(size), HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<SizesDTO> updateSize(Sizes newSize, long id) {
		Optional<Sizes> sizeOptional = sizesRepo.findById(id);
		if(sizeOptional.isPresent()) {
			Sizes size = sizeOptional.get();
			if(newSize.getSize()!=null){
				size.setSize(newSize.getSize());
			}

			if(newSize.getDescription()!=null){
				size.setDescription(newSize.getDescription());
			}
			return new ResponseEntity<>(modelToDTO.sizesToDTO(sizesRepo.save(size)),HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<Boolean> deleteSize(long id) {
		try {
			sizesRepo.deleteById(id);
			return new ResponseEntity<>(true,HttpStatus.OK);
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return new ResponseEntity<>(true,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//Product
	public List<ProductDTO> getProductsList(){
		List<Product> products = productRepo.findAll();
		List<ProductDTO> prodDTOList = new ArrayList<>();
		for(Product prod : products) {
			//Convert to product DTO class
			ProductDTO prodDTO = modelToDTO.productToDTO(prod);
			
			//Convert product images to DTO class (Retrieve Only First Image)
			List<ProductImages> productImages = prod.getProductImages();
			List<ProductImagesDTO> prodImagesDTOList = new ArrayList<>();
			for(ProductImages prodImage : productImages) {
				ProductImagesDTO prodImageDTO = modelToDTO.productImagesToDTO(prodImage);
				prodImagesDTOList.add(prodImageDTO);
				break;
			}
			prodDTO.setProductImages(prodImagesDTOList);
			
			prodDTOList.add(prodDTO);
		}
		return prodDTOList;
	}
	
	public ResponseEntity<Object> getProduct(long id){
		Optional<Product> product = productRepo.findById(id);
		if(product.isPresent()) {
			//Convert to DTO class
			ProductDTO productDTO = modelToDTO.productToDTO(product.get());
			
			//Convert product images to DTO class
			List<ProductImages> productImages = product.get().getProductImages();
			List<ProductImagesDTO> prodImagesDTOList = new ArrayList<>();
			for(ProductImages prodImage : productImages) {
				ProductImagesDTO prodImageDTO = modelToDTO.productImagesToDTO(prodImage);
				prodImagesDTOList.add(prodImageDTO);
			}
			productDTO.setProductImages(prodImagesDTOList);
			
			//Convert product sizes to DTO class
			List<ProductSizes> productSizes = product.get().getProductSizes();
			List<ProductSizesDTO> prodSizesDTOList = new ArrayList<>();
			for(ProductSizes prodSize : productSizes) {
				ProductSizesDTO prodSizeDTO = modelToDTO.productSizesToDTO(prodSize);
				prodSizesDTOList.add(prodSizeDTO);
			}
			productDTO.setProductSizes(prodSizesDTOList);
			
			return new ResponseEntity<>(productDTO,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	public boolean addProduct(Product product) {
		Date currentDate = new Date();
		product.setCreatedDate(currentDate);
		product.setModifyDate(currentDate);
		product = productRepo.save(product);
		
		//Save Product Images
		List<ProductImages> productImages = product.getProductImages();
		for(ProductImages prodImage : productImages) {
			prodImage.setProduct(product);
			prodImagesRepo.save(prodImage);
		}
		
		//Save Product Sizes
		List<ProductSizes> prodSizesList = product.getProductSizes();
		for(ProductSizes prodSize : prodSizesList) {
			prodSize.setProduct(product);
			prodSizesRepo.save(prodSize);
		}
		return true;
	}
	
	public boolean deleteProduct(long id) {
		try {
			productRepo.deleteById(id);
			return true;
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return false;
	}
	
	public boolean updateProduct(Product newProduct,long id) {
		Optional<Product> product = productRepo.findById(id);
		if(product.isPresent()) {
			//Set Product Main Details
			newProduct.setId(id);
			newProduct.setCreatedDate(product.get().getCreatedDate());
			newProduct.setModifyDate(new Date());
			
			//Update Product Main Sub Category Details
			if(newProduct.getMainSubCategory()==null) {
				newProduct.setMainSubCategory(product.get().getMainSubCategory());
			}else {
				Optional<MainSubCategory> mainSubCat = mainSubCatRepo.findById(newProduct.getMainSubCategory().getId());
				if(mainSubCat.isPresent()) {
					newProduct.setMainSubCategory(mainSubCat.get());
				}
			}
			
			productRepo.save(newProduct);
			product = productRepo.findById(id);
			
			//Update Product Images
			if(newProduct.getProductImages()!=null && product.isPresent()) {
				List<ProductImages> newProductImages = newProduct.getProductImages();
				for(ProductImages newProdImage : newProductImages) {
					ProductImages tempProdImage = prodImagesRepo.findByProductAndPath(product.get(), newProdImage.getPath());
					
					if(tempProdImage==null) {
						newProdImage.setProduct(product.get());
						prodImagesRepo.save(newProdImage);
					}
				}
			}
			
			product = productRepo.findById(id);
			//Update Product Sizes
			if(newProduct.getProductSizes()!=null && product.isPresent()) {
				System.err.println(product.get());
				List<ProductSizes> newProductSizes= newProduct.getProductSizes();
				for(ProductSizes newProdSize : newProductSizes) {
					Optional<Sizes> size = sizesRepo.findById(newProdSize.getSizes().getId());
					if(size.isPresent()) {
						ProductSizes tempProdSize = prodSizesRepo.findByProductAndSizes(product.get(), size.get());
						System.err.println(tempProdSize);
						if(tempProdSize==null) {
							newProdSize.setProduct(product.get());
							newProdSize.setSizes(size.get());
							prodSizesRepo.save(newProdSize);
						}else {
							if(tempProdSize.getQuantity()!=newProdSize.getQuantity()) {
								tempProdSize.setQuantity(newProdSize.getQuantity());
								prodSizesRepo.save(tempProdSize);
							}
						}
					}
				}
			}
			
			
			return true;
		}
		return false;
	}
}
