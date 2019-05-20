package Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import DTOClasses.CategoryDTO;
import DTOClasses.MainSubCategoryDTO;
import EnumClasses.Gender;
import ModelClasses.MainCategory;
import ModelClasses.MainSubCategory;
import ModelClasses.SubCategory;
import Repositories.MainCategoryRepository;
import Repositories.MainSubCategoryRepository;
import Repositories.SubCategoryRepository;

@Service
public class CategoryService {

	@Autowired
	MainCategoryRepository mainCatRepo;
	
	@Autowired
	MainSubCategoryRepository mainSubCatRepo;
	
	@Autowired
	SubCategoryRepository subCatRepo;
	
	//Main Category
	public Iterable<MainCategory> getMainCategoryList(String type){
		Iterable<MainCategory> mainCatList;
		if(type.equalsIgnoreCase("men")) {
			mainCatList = mainCatRepo.findByType(Gender.M);
		}else {
			mainCatList = mainCatRepo.findByType(Gender.F);
		}
		List<CategoryDTO> mainCatDTOList = new ArrayList<CategoryDTO>();
		//Retrieve all main categories
		for(MainCategory mainCat : mainCatList) {
			List<SubCategory> subCatList = mainCat.getSubCategory();
			List<SubCategory> newSubCatList = new ArrayList<>();
			for(SubCategory subCat : subCatList) {
				subCat.setMainCategory(null);
				newSubCatList.add(subCat);
			}
			mainCat.setSubCategory(newSubCatList);
		}
		return mainCatList;
	}
	
	public MainCategory addCategory(MainCategory mainCat) {
		return mainCatRepo.save(mainCat);
	}
	
	public boolean deleteCategory(Integer id) {
		try {
			mainCatRepo.deleteById(id);
			return true;
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return false;
	}
	
	public boolean updateCategory(MainCategory newMainCat, Integer id) {
		try {
			MainCategory mainCat = mainCatRepo.findById(id).get();
			newMainCat.setId(mainCat.getId());
			mainCatRepo.save(newMainCat);
			return true;
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return false;
	}
	
	//Sub Category
	public Iterable<CategoryDTO> getSubCategoryList(){
		List<SubCategory> subCatList = subCatRepo.findAll();
		List<CategoryDTO> newSubCatList = new ArrayList<CategoryDTO>();
		for(SubCategory subCat : subCatList) {
			//subCat.setMainSubCategory(null);
			CategoryDTO category = new CategoryDTO();
			category.setSubcategory(subCat);
			newSubCatList.add(category);
		}
		return newSubCatList;
	}
	
	public SubCategory addSubCategory(SubCategory subCat,int mainCatId) {
		MainCategory mainCat = mainCatRepo.findById(mainCatId).get();
		subCat= subCatRepo.save(subCat);
		
		MainSubCategory mainSubCat = new MainSubCategory();
		mainSubCat.setMainCategory(mainCat);
		mainSubCat.setSubCategory(subCat);
		mainSubCatRepo.save(mainSubCat);
		return subCat;
	}
	
	public SubCategory addSubCategory(int mainCatId,int subCatId) {
		MainCategory mainCat = mainCatRepo.findById(mainCatId).get();
		SubCategory subCat = subCatRepo.findById(subCatId).get();
		
		MainSubCategory mainSubCat = new MainSubCategory();
		mainSubCat.setMainCategory(mainCat);
		mainSubCat.setSubCategory(subCat);
		mainSubCatRepo.save(mainSubCat);
		return subCat;
	}
	
	public boolean deleteSubCategory(int id) {
		try {
			subCatRepo.deleteById(id);
			return true;
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return false;
	}
	
	public boolean updateSubCategory(SubCategory newSubCat, int id) {
		try {
			SubCategory subCat = subCatRepo.findById(id).get();
			newSubCat.setId(subCat.getId());
			subCatRepo.save(newSubCat);
			return true;
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return false;
	}
}
