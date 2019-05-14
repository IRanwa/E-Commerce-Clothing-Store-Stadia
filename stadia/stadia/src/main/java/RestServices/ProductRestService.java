package RestServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ModelClasses.Product;
import ModelClasses.Sizes;
import Services.ProductService;

@RestController
public class ProductRestService {
	@Autowired
	ProductService prodService;
	//Sizes
	@GetMapping("/Sizes")
	public List<Sizes> getSizesList() {
		return prodService.getSizesList();
	}
	
	@PostMapping("/Sizes")
	public Sizes addSize(Sizes size) {
		return prodService.addSize(size);
	}
	
	@PutMapping("/Sizes/{id}")
	public Sizes updateSize(Sizes size,@PathVariable long id) {
		return prodService.updateSize(size, id);
	}
	
	@DeleteMapping("/Sizes/{id}")
	public String deleteSize(@PathVariable long id) {
		return "{status:"+prodService.deleteSize(id)+"}";
	}
	
	//Products
	@GetMapping("/Product")
	public List<Product> getProductList() {
		return prodService.getProductList();
	}
	
	@PostMapping("/Product")
	public String addProduct(Product product) {
		return "{status:"+prodService.addProduct(product)+"}";
		//return product;
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
