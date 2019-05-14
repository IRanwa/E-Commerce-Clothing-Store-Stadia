package Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ModelClasses.MainSubCategory;
import ModelClasses.Product;
import ModelClasses.ProductSizes;
import ModelClasses.Sizes;
import Repositories.MainSubCategoryRepository;
import Repositories.ProductRepository;
import Repositories.ProductSizesRepository;
import Repositories.SizesRepository;

@Service
public class ProductService {
	@Autowired
	SizesRepository sizesRepo;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	ProductSizesRepository prodSizesRepo;
	
	@Autowired
	MainSubCategoryRepository mainSubCatRepo;
	
	//Sizes
	public List<Sizes> getSizesList() {
		List<Sizes> sizesList = sizesRepo.findAll();
		for(Sizes size : sizesList) {
			size.setProductSizes(null);
		}
		return sizesRepo.findAll();
	}
	
	public Sizes addSize(Sizes size) {
		return sizesRepo.save(size);
	}
	
	public Sizes updateSize(Sizes newSize,long id) {
		Optional<Sizes> size = sizesRepo.findById(id);
		if(size.isPresent()) {
			newSize.setId(size.get().getId());
			return sizesRepo.save(newSize);
		}
		return null;
	}

	public boolean deleteSize(long id) {
		try {
			sizesRepo.deleteById(id);
			return true;
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return false;
	}
	
	//Product
	public List<Product> getProductList() {
		List<Product> productsList = productRepo.findAll();
		for(Product prod : productsList) {
			prod.setProductSizes(null);
			prod.setMainSubCategory(null);
//			List<ProductSizes> sizesList = prod.getProductSizes();
//			for(ProductSizes size : sizesList) {
//				size.setProduct(null);
//				size.setSizes(null);
//			}
		}
		return productsList;
	}
	
	public boolean addProduct(Product product) {
		long mainSubCatId = product.getMainSubCategory().getId();
		Optional<MainSubCategory> mainSubCat = mainSubCatRepo.findById(mainSubCatId);
		if(mainSubCat.isPresent()) {
			product.setMainSubCategory(mainSubCat.get());
			
			product = productRepo.save(product);
			System.out.println("id : "+product.getId());
			List<ProductSizes> prodSizesList = product.getProductSizes();
			for(ProductSizes prodSize : prodSizesList) {
				prodSize.setProduct(product);
				Optional<Sizes> size = sizesRepo.findById(prodSize.getSizes().getId());
				if(size.isPresent()) {
					prodSize.setSizes(size.get());
				}
				prodSizesRepo.save(prodSize);
			}
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
			newProduct.setId(product.get().getId());
			newProduct.setCreatedDate(product.get().getCreatedDate());
			newProduct.setModifyDate(new Date());
			newProduct.setMainSubCategory(product.get().getMainSubCategory());
			newProduct = productRepo.save(newProduct);
			return true;
		}
		return false;
	}
}
