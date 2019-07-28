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

	@GetMapping("/GetMainCategory/{id}")
	public ResponseEntity<MainCategoryDTO> GetMainCategory(@PathVariable int id){
		return catService.getMainCategory(id);
	}

	@PostMapping("/AddMainCategory")
	public ResponseEntity<MainCategoryDTO> AddMainCategory(@RequestBody MainCategory mainCategory) {
		MainCategoryDTO mainCatDTO = catService.addMainCategory(mainCategory);
		if(mainCategory!=null) {
			return new ResponseEntity<>(mainCatDTO,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/DeleteMainCategory/{id}")
	public ResponseEntity<Boolean> DeleteMainCategory(@PathVariable int id) {
		return catService.deleteMainCategory(id);
	}
	
	@PutMapping("/UpdateMainCategory/{id}")
	public ResponseEntity<MainCategoryDTO> UpdateMainCategory(@PathVariable int id, @RequestBody MainCategory mainCat) {
		return catService.updateCategory(mainCat,id);
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

	@GetMapping("/GetSubCategory/{id}")
	public ResponseEntity<SubCategoryDTO> GetSubCategory(@PathVariable int id){
		return catService.getSubCategory(id);
	}

	@GetMapping("/MainSubCategory/{id}")
	public List<SubCategoryDTO> GetMainSubCategory(@PathVariable int id){
		return catService.getMainSubCategory(id);
	}

	@PostMapping("/AddSubCategory/{mainCatId}")
	public ResponseEntity<SubCategory> AddSubCategory(@PathVariable int mainCatId, @RequestBody SubCategory subCat) {
		subCat = catService.addSubCategory(subCat, mainCatId);
		if(subCat!=null) {
			return new ResponseEntity<>(subCat,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/DeleteSubCategory/{id}")
	public ResponseEntity<Boolean> DeleteSubCategory(@PathVariable int id) {
		return catService.deleteSubCategory(id);
	}
	
	@PutMapping("/UpdateSubCategory/{id}")
	public ResponseEntity<SubCategoryDTO> UpdateSubCategory(@PathVariable int id, @RequestBody SubCategory subCat) {
		return catService.updateSubCategory(subCat,id);
	}
}
