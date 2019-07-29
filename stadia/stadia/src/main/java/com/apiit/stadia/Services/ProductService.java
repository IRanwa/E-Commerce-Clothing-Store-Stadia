package com.apiit.stadia.Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apiit.stadia.DTOClasses.*;
import com.apiit.stadia.ModelClasses.*;
import com.apiit.stadia.Repositories.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
	@Autowired
	SubCategoryRepository subCatRepo;
	
	
	//Model Class to DTO
	@Autowired
	ModelClassToDTO modelToDTO;

	private final int PAGE_COUNT = 1;
	
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
	public double getProductPages() {
		return Math.ceil(Double.valueOf(productRepo.count())/PAGE_COUNT);
	}

	public List<ProductDTO> getProductsList(int pageNo){
		List<ProductDTO> prodDTOList = new ArrayList<>();
		if(pageNo>=0) {
			Pageable pages = PageRequest.of(pageNo, PAGE_COUNT);

			Page<Product> prodList = productRepo.findAll(pages);
			for(Product prod : prodList) {
				prodDTOList.add(modelToDTO.productToDTO(prod));
			}
		}
		return prodDTOList;
	}
	
	public ResponseEntity<Object> getProduct(long id){
		Optional<Product> product = productRepo.findById(id);
		if(product.isPresent()) {
			//Convert to DTO class
			ProductDTO productDTO = modelToDTO.productToDTO(product.get());
			
//			//Convert product images to DTO class
//			List<ProductImages> productImages = product.get().getProductImages();
//			List<ProductImagesDTO> prodImagesDTOList = new ArrayList<>();
//			for(ProductImages prodImage : productImages) {
//				ProductImagesDTO prodImageDTO = modelToDTO.productImagesToDTO(prodImage);
//				prodImagesDTOList.add(prodImageDTO);
//			}
//			productDTO.setProductImages(prodImagesDTOList);
//
//			//Convert product sizes to DTO class
//			List<ProductSizes> productSizes = product.get().getProductSizes();
//			List<ProductSizesDTO> prodSizesDTOList = new ArrayList<>();
//			for(ProductSizes prodSize : productSizes) {
//				ProductSizesDTO prodSizeDTO = modelToDTO.productSizesToDTO(prodSize);
//				prodSizesDTOList.add(prodSizeDTO);
//			}
//			productDTO.setProductSizes(prodSizesDTOList);
			
			return new ResponseEntity<>(productDTO,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	public boolean addProduct(Product product) {
		//Find MainSubCategory
		MainSubCategory mainSubCat = product.getMainSubCategory();
		Optional<MainCategory> mainCat = mainCatRepo.findById(mainSubCat.getMainCategory().getId());
		Optional<SubCategory> subCat = subCatRepo.findById(mainSubCat.getSubCategory().getId());
		mainSubCat = mainSubCatRepo.findByMainCategoryAndSubCategory(mainCat.get(), subCat.get());
		product.setMainSubCategory(mainSubCat);

		//Save Product
		Date currentDate = new Date();
		product.setCreatedDate(currentDate);
		product.setModifyDate(currentDate);
		product = productRepo.save(product);

		//Save Product Sizes
		List<ProductSizes> prodSizes = product.getProductSizes();
		for(ProductSizes prodSize : prodSizes){
			List<Sizes> sizes = sizesRepo.findBySize(prodSize.getSizes().getSize());
			if(sizes.size()>0){
				prodSize.setSizes(sizes.get(0));
				prodSize.setProduct(product);
				prodSizesRepo.save(prodSize);
			}
		}

		//Save Product Image
		String subPath = "/Images/Products/"+product.getId()+"/";
		String path = System.getProperty("user.dir") + subPath;
		File dir = new File(path);
		boolean status = dir.mkdir();
		if(status) {
			List<ProductImages> images = product.getProductImages();
			for (ProductImages prodImage : images) {
				String imageName = saveImage(subPath,product.getTitle(),prodImage.getPath());
				prodImage.setProduct(product);
				prodImage.setPath(imageName);
				prodImagesRepo.save(prodImage);
			}

		}
		return true;
	}
	
	public ResponseEntity<Boolean> deleteProduct(long id) {
		try {
			Optional<Product> productOptional = productRepo.findById(id);
			if(productOptional.isPresent()){
				Product prod = productOptional.get();

				//Delete Images from Server
				boolean status = deleteImageFolder("/Images/Products/"+prod.getId()+"/");
				if(status){
					productRepo.deleteById(id);
					return new ResponseEntity<>(true,HttpStatus.OK);
				}
//				List<ProductImages> prodImages = prod.getProductImages();
//				for(ProductImages image : prodImages){
//					boolean status = deleteImage("/Images/Products/"+prod.getId()+"/"+image);
//					if(!status){
//						return new ResponseEntity<>(true,HttpStatus.OK);
//					}
//				}
			}

		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
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

	private String saveImage(String subPath,String title, String image){
		String path = System.getProperty("user.dir") + subPath;
		String imageName = title + ".png";

		File file = new File(path + imageName);
		int count = 1;
		while (file.exists()) {
			imageName = title + "_" + count + ".png";
			file = new File(path + imageName);
			count++;
		}

		String base64 = image.split(",")[1];
		byte[] data = Base64.decodeBase64(base64);

		try {
			FileOutputStream out = new FileOutputStream(new File(path + imageName));
			out.write(data);
			out.flush();
			out.close();
			return imageName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return null;
	}

	public boolean deleteImageFolder(String folderPath){
		String path = System.getProperty("user.dir") + folderPath;
		File folder = new File(path);
		String[]entries = folder.list();
		for(String s: entries){
			File currentFile = new File(folder.getPath(),s);
			currentFile.delete();
		}
		boolean status = folder.delete();
		return status;
	}
}
