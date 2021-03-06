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
import com.apiit.stadia.EnumClasses.SortBy;
import com.apiit.stadia.ModelClasses.*;
import com.apiit.stadia.Repositories.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	@Autowired
	DTOToModelClass dtoToModel;

	private final int PAGE_COUNT = 10;
	
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

	public List<ProductDTO> getProductsList(int pageNo,ProductDTO productDTO){
		List<ProductDTO> prodDTOList = new ArrayList<>();
		if(pageNo>=0) {
			if(productDTO.getSortBy()==null) {
				Pageable pages = PageRequest.of(pageNo, PAGE_COUNT);
				Page<Product> prodList = productRepo.findAll(pages);
				for (Product prod : prodList) {
					prodDTOList.add(modelToDTO.productToDTO(prod));
				}
			}else{
				SortBy sortBy = productDTO.getSortBy();
				Page<Product> prodList = null;
				Pageable pages = PageRequest.of(pageNo, PAGE_COUNT);
				switch(sortBy){
					case Date_Newest:
						pages = PageRequest.of(pageNo, PAGE_COUNT, Sort.by(Sort.Direction.DESC, "modifyDate"));
						//prodList = productRepo.findAll(pages);
						break;
					case Date_Oldest:
						pages = PageRequest.of(pageNo, PAGE_COUNT, Sort.by(Sort.Direction.ASC, "modifyDate"));

						//prodList = productRepo.findAll(pages);
						break;
					case A_To_Z:
						pages = PageRequest.of(pageNo, PAGE_COUNT, Sort.by(Sort.Direction.ASC, "title"));

						//prodList = productRepo.findAll(pages);
						break;
					case Z_To_A:
						pages = PageRequest.of(pageNo, PAGE_COUNT, Sort.by(Sort.Direction.DESC, "title"));

						//prodList = productRepo.findAll(pages);
						break;
				}
				MainSubCategoryDTO mainSubCatDTO = productDTO.getMainSubCategory();
				if(mainSubCatDTO!=null) {
					if (mainSubCatDTO.getMainCategory() != null) {
						Optional<MainCategory> mainCatOptional = mainCatRepo.findById(mainSubCatDTO.getMainCategory().getId());
						if (mainCatOptional.isPresent()) {
							Optional<SubCategory> subCatOptional = subCatRepo.findById(mainSubCatDTO.getSubCategory().getId());
							if (subCatOptional.isPresent()) {
								MainSubCategory mainSubCat = mainSubCatRepo.findByMainCategoryAndSubCategory(mainCatOptional.get(), subCatOptional.get());
								prodList = productRepo.findByMainSubCategory(mainSubCat, pages);
							} else {

							}
						} else {
							prodList = productRepo.findAll(pages);
						}
					} else {
						prodList = productRepo.findAll(pages);
					}
				}else{
					prodList = productRepo.findAll(pages);
				}
				for (Product prod : prodList) {
					prodDTOList.add(modelToDTO.productToDTO(prod));
				}

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
	
	public boolean updateProduct(ProductDTO newProductDTO,long id) {
		Optional<Product> productOptional = productRepo.findById(id);
		if(productOptional.isPresent()) {
			Product product = productOptional.get();
			//Set Product Main Details
			product.setModifyDate(new Date());

			if(!newProductDTO.getTitle().equals(product.getTitle())){
				product.setTitle(newProductDTO.getTitle());
			}
			if(!newProductDTO.getDescription().equals(product.getDescription())){
				product.setDescription(newProductDTO.getDescription());
			}
			if(newProductDTO.getPrice()!=product.getPrice()){
				product.setPrice(newProductDTO.getPrice());
			}
			
			//Update Product Main Sub Category Details
			MainSubCategory currentMainSubCat = product.getMainSubCategory();
			MainSubCategoryDTO newMainSubCat = newProductDTO.getMainSubCategory();
			if(currentMainSubCat.getMainCategory().getId()!=newMainSubCat.getMainCategory().getId() ||
			currentMainSubCat.getSubCategory().getId()!=newMainSubCat.getSubCategory().getId()){
				Optional<MainCategory> mainCatOptional = mainCatRepo.findById(newMainSubCat.getMainCategory().getId());
				Optional<SubCategory> subCatOptional = subCatRepo.findById(newMainSubCat.getSubCategory().getId());

				if(mainCatOptional.isPresent() && subCatOptional.isPresent()){
					MainSubCategory mainSubCat = mainSubCatRepo.findByMainCategoryAndSubCategory(mainCatOptional.get(), subCatOptional.get());
					product.setMainSubCategory(mainSubCat);
				}
			}

			//Save Product
			product = productRepo.save(product);
			
			//Update Product Images
			List<ProductImagesDTO> newProductImages = newProductDTO.getProductImages();

			boolean deleted = deleteImageFolder("/Images/Products/" + product.getId() + "/");
			if(deleted){
				for(ProductImages prodImage : product.getProductImages()){
					prodImagesRepo.delete(prodImage);
				}
				String subPath = "/Images/Products/"+product.getId()+"/";
				String path = System.getProperty("user.dir") + subPath;
				File dir = new File(path);
				boolean status = dir.mkdir();
				if(status) {
					for (ProductImagesDTO newImage : newProductImages) {
						String savedPath = saveImage("/Images/Products/" + product.getId() + "/", product.getTitle(), newImage.getPath());
						ProductImages prodImage = new ProductImages();
						prodImage.setPath(savedPath);
						prodImage.setProduct(product);
						prodImagesRepo.save(prodImage);
					}
				}
			}

			//Update Product Sizes
			List<ProductSizesDTO> newProductSizes = newProductDTO.getProductSizes();
			for(ProductSizesDTO newProdSize : newProductSizes) {
				List<Sizes> sizes = sizesRepo.findBySize(newProdSize.getSizes().getSize());
				if(sizes.size()>0){
					ProductSizes prodSizes = prodSizesRepo.findByProductAndSizes(product, sizes.get(0));
					if(prodSizes==null){
						prodSizes = new ProductSizes();
						prodSizes.setSizes(sizes.get(0));
						prodSizes.setProduct(product);
						prodSizes.setQuantity(newProdSize.getQuantity());
					}else{
						prodSizes.setQuantity(newProdSize.getQuantity());
					}
					prodSizesRepo.save(prodSizes);
				}
			}

			List<ProductSizes> currentProdSizes = product.getProductSizes();
			for(ProductSizes prodSize: currentProdSizes){
				boolean status = false;
				for(ProductSizesDTO newProdSize : newProductSizes) {
					if(prodSize.getSizes().getSize().equals(newProdSize.getSizes().getSize())){
						status = true;
					}
				}
				if(!status){
					prodSizesRepo.delete(prodSize);
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
		if(folder.exists()) {
			String[] entries = folder.list();
			if (entries != null) {
				for (String s : entries) {
					File currentFile = new File(folder.getPath(), s);
					currentFile.delete();
				}
			}
			boolean status = folder.delete();
			return status;
		}
		return true;

	}
}
