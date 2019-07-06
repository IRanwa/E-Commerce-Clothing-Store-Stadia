package com.apiit.stadia.RestController;

import java.util.List;

import com.apiit.stadia.DTOClasses.SizesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiit.stadia.DTOClasses.ProductDTO;
import com.apiit.stadia.ModelClasses.Product;
import com.apiit.stadia.ModelClasses.Sizes;
import com.apiit.stadia.Services.ProductService;

@RestController
@CrossOrigin("*")
public class ProductRestService {
	@Autowired
	ProductService prodService;
	
	//Sizes
	@GetMapping("/Sizes/{pageNo}")
	public List<SizesDTO> getSizes(@PathVariable int pageNo) {
		return prodService.getSizesList(pageNo);
	}

	@GetMapping("/Sizes/Pages")
	public double getSizesPageCount() {
		return prodService.getSizesPages();
	}

	@GetMapping("/GetSize/{id}")
	public ResponseEntity<SizesDTO> getSize(@PathVariable long id){
		return prodService.getSize(id);
	}
	
	@PostMapping("/AddSizes")
	public ResponseEntity<SizesDTO> addSize(@RequestBody Sizes size) {
		return prodService.addSize(size);
	}
	
	@PutMapping("/UpdateSizes/{id}")
	public ResponseEntity<SizesDTO> updateSize(@RequestBody Sizes size,@PathVariable long id) {
		return prodService.updateSize(size, id);
	}
	
	@DeleteMapping("/Sizes/{id}")
	public String deleteSize(@PathVariable long id) {
		return "{status:"+prodService.deleteSize(id)+"}";
	}
	
	//Products
//	@GetMapping("/Product/{mainCat}/{orderBy}")
//	public List<Product> getSizesList(@PathVariable int mainCat,@PathVariable boolean orderBy) {
//		return prodService.getProductList(mainCat,orderBy);
//	}
	
	@GetMapping("/Product")
	public List<ProductDTO> getProductList(){
		return prodService.getProductsList();
	}
	
	@GetMapping("/Product/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable long id){
		return prodService.getProduct(id);
	}
	
	@PostMapping("/Product")
	public String addProduct(Product product) {
		return "{status:"+prodService.addProduct(product)+"}";
	}
	
	@DeleteMapping("/Product/{id}")
	public String deleteProduct(@PathVariable long id) {
		return "{status:"+prodService.deleteProduct(id)+"}";
	}
	
	@PutMapping("/Product/{id}")
	public String updateProduct(Product product, @PathVariable long id) {
		return "{Status:"+prodService.updateProduct(product, id)+"}";
	}
	
	//Product Size
	
}
