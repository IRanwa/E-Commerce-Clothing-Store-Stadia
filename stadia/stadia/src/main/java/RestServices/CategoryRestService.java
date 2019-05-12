package RestServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import DTOClasses.MainCategoryDTO;
import EnumClasses.Gender;
import EnumClasses.UserRole;
import ModelClasses.MainCategory;
import ModelClasses.MainSubCategory;
import ModelClasses.SubCategory;
import Repositories.MainCategoryRepository;
import Services.CategoryService;

@RestController
public class CategoryRestService {
	
	@Autowired
	CategoryService catService;
	
	@Autowired
	MainCategoryRepository mainCatRepo;

	//Maincategory
	
	@GetMapping("/MainCategory")
	public List<MainCategoryDTO> GetMainCategories() {
		return catService.GetMainCategoryList();
	}
	
	@PostMapping("/MainCategory")
	public MainCategory AddMainCategory() {
		MainCategory mainCat = new MainCategory();
		mainCat.setMainCatDesc("Cat3");
		mainCat.setMainCatTitle("Cat3");
		mainCat.setMainCatImg("Cat3");
		mainCat.setType(Gender.F);
		return catService.AddCategory(mainCat);
	}
	
	@DeleteMapping("/MainCategory/{id}")
	public String DeleteMainCategory(@PathVariable Integer id) {
		return "{\"Status\":"+catService.DeleteCategory(id)+"}";
	}
	
	@PutMapping("/MainCategory/{id}")
	public String UpdateMainCategory(@PathVariable Integer id) {
		MainCategory mainCat = new MainCategory();
		mainCat.setMainCatDesc("Cat4");
		mainCat.setMainCatTitle("Cat4");
		mainCat.setMainCatImg("Cat4");
		mainCat.setType(Gender.F);
		return "{\"Status\":"+catService.UpdateCategory(mainCat,id)+"}";
	}
	
	//SubCategory
	@GetMapping("/SubCategory")
	public Iterable<SubCategory> GetSubCategories() {
		return catService.GetSubCategoryList();
	}
	
	@PostMapping("/SubCategory/{id}")
	public SubCategory AddSubCategory(@PathVariable int id) {
		SubCategory subCat = new SubCategory();
		subCat.setSubCatTitle("SubCat1");
		subCat.setSubCatDesc("SubCat1");
		subCat.setSubCatImg("SubCat1");
		return catService.AddSubCategory(subCat,id);
	}
	
	@PostMapping("/SubCategory/{mainId}/{subId}")
	public SubCategory AddSubCategory(@PathVariable int mainId,@PathVariable int subId) {
		return catService.AddSubCategory(mainId,subId);
	}
	
	@DeleteMapping("/SubCategory/{id}")
	public String DeleteSubCategory(@PathVariable int id) {
		return "{\"Status\":"+catService.DeleteSubCategory(id)+"}";
	}
	
	@PutMapping("/SubCategory/{id}")
	public String UpdateSubCategory(@PathVariable int id) {
		SubCategory subCat = new SubCategory();
		subCat.setSubCatDesc("SubCatUpdate1");
		subCat.setSubCatTitle("SubCatUpdate1");
		subCat.setSubCatImg("SubCatUpdate1");
		return "{\"Status\":"+catService.UpdateSubCategory(subCat,id)+"}";
	}
}
