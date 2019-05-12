package Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import DTOClasses.MainCategoryDTO;
import DTOClasses.MainSubCategoryDTO;
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
	public List<MainCategoryDTO> GetMainCategoryList(){
		Iterable<MainCategory> mainCatList = mainCatRepo.findAll();
		List<MainCategoryDTO> mainCatDTOList = new ArrayList<MainCategoryDTO>();
		//Retrieve all main categories
		for(MainCategory mainCat : mainCatList) {
			List<MainSubCategory> mainSubcatList = mainCat.getMainSubCategory();
			List<MainSubCategoryDTO> mainSubCatDTOList = new ArrayList<MainSubCategoryDTO>();
			//Retrieve all MainSubCategories
			for(MainSubCategory mainSubCat : mainSubcatList) {
				//Create MainSubCategoryDTO class
				MainSubCategoryDTO newMainSubCat = new MainSubCategoryDTO();
				newMainSubCat.setId(mainSubCat.getId());
				newMainSubCat.setMainCatId(mainSubCat.getMainCategory().getId());
				newMainSubCat.setSubCatId(mainSubCat.getSubCategory().getId());
				mainSubCatDTOList.add(newMainSubCat);
			}
			//Create MainCategoryDTO class
			mainCat.setMainSubCategory(null);
			MainCategoryDTO mainCatDTO = new MainCategoryDTO();
			mainCatDTO.setMainCategory(mainCat);
			mainCatDTO.setMainSubCategory(mainSubCatDTOList);
			mainCatDTOList.add(mainCatDTO);
		}
		return mainCatDTOList;
	}
	
	public MainCategory AddCategory(MainCategory mainCat) {
		return mainCatRepo.save(mainCat);
	}
	
	public boolean DeleteCategory(Integer id) {
		try {
			mainCatRepo.deleteById(id);
			return true;
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return false;
	}
	
	public boolean UpdateCategory(MainCategory newMainCat, Integer id) {
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
	public Iterable<SubCategory> GetSubCategoryList(){
		return subCatRepo.findAll();
	}
	
	public SubCategory AddSubCategory(SubCategory subCat,int mainCatId) {
		MainCategory mainCat = mainCatRepo.findById(mainCatId).get();
		subCat= subCatRepo.save(subCat);
		
		MainSubCategory mainSubCat = new MainSubCategory();
		mainSubCat.setMainCategory(mainCat);
		mainSubCat.setSubCategory(subCat);
		mainSubCatRepo.save(mainSubCat);
		return subCat;
	}
	
	public SubCategory AddSubCategory(int mainCatId,int subCatId) {
		MainCategory mainCat = mainCatRepo.findById(mainCatId).get();
		SubCategory subCat = subCatRepo.findById(subCatId).get();
		
		MainSubCategory mainSubCat = new MainSubCategory();
		mainSubCat.setMainCategory(mainCat);
		mainSubCat.setSubCategory(subCat);
		mainSubCatRepo.save(mainSubCat);
		return subCat;
	}
	
	public boolean DeleteSubCategory(int id) {
		try {
			subCatRepo.deleteById(id);
			return true;
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return false;
	}
	
	public boolean UpdateSubCategory(SubCategory newSubCat, int id) {
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
