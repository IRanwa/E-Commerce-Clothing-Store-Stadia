package com.apiit.stadia.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apiit.stadia.DTOClasses.CategoryDTO;
import com.apiit.stadia.DTOClasses.MainCategoryDTO;
import com.apiit.stadia.DTOClasses.SubCategoryDTO;
import com.apiit.stadia.EnumClasses.Gender;
import com.apiit.stadia.ModelClasses.MainCategory;
import com.apiit.stadia.ModelClasses.SubCategory;
import com.apiit.stadia.Repositories.MainCategoryRepository;
import com.apiit.stadia.Services.CategoryService;
import com.apiit.stadia.Services.DTOToModelClass;

@CrossOrigin("*")
@RestController
public class CategoryRestService {
	
	@Autowired
	CategoryService catService;

	//Maincategory
	@GetMapping("/MainCategory")
	public List<MainCategoryDTO> GetAllMainCategory() {
		return catService.getMainCategoryList();
	}

	@GetMapping("/MainCategory/{pageNo}")
	public List<MainCategoryDTO> GetAllMainCategory(@PathVariable int pageNo) {
		return catService.getMainCategoryList(pageNo);
	}
	
	@GetMapping("/MainCategory/Pages")
	public double getMainCategoryPageCount() {
		return catService.getMainCatPages();
	}

	@PostMapping("/AddMainCategory")
	public ResponseEntity<MainCategoryDTO> AddMainCategory(@RequestBody MainCategory mainCategory) {
		MainCategoryDTO mainCatDTO = catService.addMainCategory(mainCategory);
		if(mainCategory!=null) {
			return new ResponseEntity<MainCategoryDTO>(mainCatDTO,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/MainCategory/{id}")
	public String DeleteMainCategory(@PathVariable Integer id) {
		return "{\"Status\":"+catService.deleteCategory(id)+"}";
	}
	
	@PutMapping("/MainCategory/{id}")
	public String UpdateMainCategory(@PathVariable Integer id) {
		MainCategory mainCat = new MainCategory();
		mainCat.setMainCatDesc("Cat4");
		mainCat.setMainCatTitle("Cat4");
		mainCat.setMainCatImg("Cat4");
		mainCat.setType(Gender.F);
		return "{\"Status\":"+catService.updateCategory(mainCat,id)+"}";
	}
	
	//SubCategory

	@GetMapping("/SubCategory/{pageNo}")
	public List<SubCategoryDTO> GetSubCategories(@PathVariable int pageNo) {
		return catService.getSubCategoryList(pageNo);
	}
	
	@GetMapping("/SubCategory/Pages")
	public double getSubCategoryPageCount() {
		return catService.getSubCatPages();
	}
	
	@PostMapping("/AddSubCategory/{mainCatId}")
	public ResponseEntity<SubCategory> AddSubCategory(@PathVariable int mainCatId, @RequestBody SubCategory subCat) {
		subCat = catService.addSubCategory(subCat, mainCatId);
		if(subCat!=null) {
			return new ResponseEntity<SubCategory>(subCat,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/SubCategory/{mainId}/{subId}")
	public SubCategory AddSubCategory(@PathVariable int mainId,@PathVariable int subId) {
		return catService.addSubCategory(mainId,subId);
	}
	
	@DeleteMapping("/SubCategory/{id}")
	public String DeleteSubCategory(@PathVariable int id) {
		return "{\"Status\":"+catService.deleteSubCategory(id)+"}";
	}
	
	@PutMapping("/SubCategory/{id}")
	public String UpdateSubCategory(@PathVariable int id) {
		SubCategory subCat = new SubCategory();
		subCat.setSubCatDesc("SubCatUpdate1");
		subCat.setSubCatTitle("SubCatUpdate1");
		//subCat.setSubCatImg("SubCatUpdate1");
		return "{\"Status\":"+catService.updateSubCategory(subCat,id)+"}";
	}
}
