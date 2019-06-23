package com.apiit.stadia.Services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.apiit.stadia.DTOClasses.CategoryDTO;
import com.apiit.stadia.DTOClasses.MainCategoryDTO;
import com.apiit.stadia.DTOClasses.MainSubCategoryDTO;
import com.apiit.stadia.DTOClasses.SubCategoryDTO;
import com.apiit.stadia.EnumClasses.Gender;
import com.apiit.stadia.ModelClasses.MainCategory;
import com.apiit.stadia.ModelClasses.MainSubCategory;
import com.apiit.stadia.ModelClasses.SubCategory;
import com.apiit.stadia.Repositories.MainCategoryRepository;
import com.apiit.stadia.Repositories.MainSubCategoryRepository;
import com.apiit.stadia.Repositories.SubCategoryRepository;

@Service
public class CategoryService {

	@Autowired
	MainCategoryRepository mainCatRepo;
	
	@Autowired
	MainSubCategoryRepository mainSubCatRepo;
	
	@Autowired
	SubCategoryRepository subCatRepo;
	
	@Autowired
	ModelClassToDTO modelToDTO;
	
	private final int pageCount = 2;
	//Main Category

	public List<MainCategoryDTO> getMainCategoryList(){
		List<MainCategory> mainCatList = mainCatRepo.findAll();
		List<MainCategoryDTO> mainCatDTOList = new ArrayList<>();
		for(MainCategory mainCat : mainCatList) {
			mainCatDTOList.add(modelToDTO.mainCategoryToDTO(mainCat));
		}
		return mainCatDTOList;
	}

	public List<MainCategoryDTO> getMainCategoryList(int pageNo){
		Pageable pages = PageRequest.of(pageNo, pageCount);
		
		Page<MainCategory> mainCatList = mainCatRepo.findAll(pages);
		List<MainCategoryDTO> mainCatDTOList = new ArrayList<>();
		for(MainCategory mainCat : mainCatList) {
			mainCatDTOList.add(modelToDTO.mainCategoryToDTO(mainCat));
		}
		return mainCatDTOList;
	}
	
	public double getMainCatPages() {
		return Math.ceil(Double.valueOf(mainCatRepo.count())/pageCount);
	}
//	public Iterable<MainCategoryDTO> getMainCategoryList(String type){
//		Iterable<MainCategory> mainCatList;
//		if(type.equalsIgnoreCase("men")) {
//			mainCatList = mainCatRepo.findByType(Gender.M);
//		}else {
//			mainCatList = mainCatRepo.findByType(Gender.F);
//		}
//		List<MainCategoryDTO> mainCatDTOList = mainCategoryToDTO(mainCatList);
//		return mainCatDTOList;
//	}
	
	public MainCategoryDTO addMainCategory(MainCategory mainCategory) {
		Optional<MainCategory> mainCatOptional = mainCatRepo.findById(mainCategory.getId());
		SubCategory subcat = null;
		
		if(!mainCatOptional.isPresent()) {
			String path = System.getProperty("user.dir") + "/Images/MainCategory/";
			String imageName = mainCategory.getMainCatTitle() + ".png";

			File file = new File(path + imageName);
			int count = 1;
			while (file.exists()) {
				imageName = mainCategory.getMainCatTitle() + "_" + count + ".png";
				file = new File(path + imageName);
				count++;
			}

			String base64 = mainCategory.getMainCatImg().split(",")[1];
			byte[] data = Base64.decodeBase64(base64);

			try {
				FileOutputStream out = new FileOutputStream(new File(path + imageName));
				out.write(data);
				out.flush();
				out.close();

				mainCategory.setMainCatImg(imageName);

				subcat = mainCategory.getSubCategory().get(0);
				mainCategory.setSubCategory(new ArrayList<>());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}else {
			subcat = mainCategory.getSubCategory().get(0);
			mainCategory = mainCatOptional.get();
		}

		Optional<SubCategory> subCatOptional = subCatRepo.findById(subcat.getId());
		if(!subCatOptional.isPresent()){
			String subCatImage = saveSubCatImage(subcat);
			subcat.setSubCatImg(subCatImage);
			System.err.println(mainCategory.getSubCategory().size());

			subcat = subCatRepo.save(subcat);
		}

		if (mainCategory != null) {
			mainCategory = mainCatRepo.save(mainCategory);

			List<MainSubCategory> mainSubCatList = mainSubCatRepo.findByMainCategoryAndSubCategory(mainCategory, subcat);
			if(mainSubCatList.size()==0) {
				MainSubCategory mainSubCat = new MainSubCategory();
				mainSubCat.setMainCategory(mainCategory);
				mainSubCat.setSubCategory(subcat);
				mainSubCatRepo.save(mainSubCat);
			}
			return modelToDTO.mainCategoryToDTO(mainCategory);
		}
		return null;
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
	public double getSubCatPages() {
		return Math.ceil(Double.valueOf(subCatRepo.count())/pageCount);
	}

	public List<SubCategoryDTO> getSubCategoryList(int pageNo){

		List<SubCategoryDTO> newSubCatList = new ArrayList<>();
		if(pageNo>=0) {
			Pageable pages = PageRequest.of(pageNo, pageCount);

			Page<SubCategory> subCatList = subCatRepo.findAll(pages);
			for(SubCategory subCat : subCatList) {
				newSubCatList.add(modelToDTO.subCategoryToDTO(subCat));
			}
		}else{
			List<SubCategory> subCatList = subCatRepo.findAll();
			for(SubCategory subCat : subCatList) {
				newSubCatList.add(modelToDTO.subCategoryToDTO(subCat));
			}
		}
		return newSubCatList;
	}
	
	public SubCategory addSubCategory(SubCategory subCat,int mainCatId) {
		String imageName = saveSubCatImage(subCat);
		if(imageName!=null) {
			subCat.setSubCatImg(imageName);
			subCat = subCatRepo.save(subCat);
			
		    MainCategory mainCat = mainCatRepo.findById(mainCatId).get();
		    MainSubCategory mainSubCategory = new MainSubCategory();
		    mainSubCategory.setMainCategory(mainCat);
		    mainSubCategory.setSubCategory(subCat);
		    mainSubCatRepo.save(mainSubCategory);
		    
			return subCat;
		}
		return null;
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
	
	private List<MainCategoryDTO> mainCategoryToDTO(Iterable<MainCategory> mainCatList) {
		//Main Category DTO List
		List<MainCategoryDTO> mainCatDTOList = new ArrayList<MainCategoryDTO>();
		for(MainCategory mainCat: mainCatList) {
			//Main Category DTO
			MainCategoryDTO mainCatDTO = new MainCategoryDTO();
			mainCatDTO.setId(mainCat.getId());
			mainCatDTO.setMainCatTitle(mainCat.getMainCatTitle());
			mainCatDTO.setMainCatDesc(mainCat.getMainCatDesc());
			mainCatDTO.setMainCatImg(mainCat.getMainCatImg());
			
			//Sub Category DTO List
			List<SubCategory> subCatList = mainCat.getSubCategory();
			List<SubCategoryDTO> subCatDTOList = new ArrayList<SubCategoryDTO>();
			for(SubCategory subCat : subCatList) {
				//New Sub Category DTO
				SubCategoryDTO subCatDTO = new SubCategoryDTO();
				subCatDTO.setId(subCat.getId());
				subCatDTO.setSubCatTitle(subCat.getSubCatTitle());
				subCatDTO.setSubCatDesc(subCat.getSubCatDesc());
				subCatDTO.setSubCatImg(subCat.getSubCatImg());
				subCatDTOList.add(subCatDTO);
			}
			mainCatDTO.setSubCategoryDTO(subCatDTOList);
			mainCatDTOList.add(mainCatDTO);
		}
		return mainCatDTOList;
	}
	
	public String saveSubCatImage(SubCategory subCat) {
		String path = System.getProperty("user.dir")+"/Images/SubCategory/";
		String imageName = subCat.getSubCatTitle()+".png";
		
		File file = new File(path+imageName);
		int count = 1;
		while(file.exists()) {
			imageName = subCat.getSubCatTitle()+"_"+count+".png";
			file = new File(path+imageName);
			count++;
		}
		
		String base64 = subCat.getSubCatImg().split(",")[1];
		byte[] data = Base64.decodeBase64(base64);
		
		try {
			FileOutputStream out = new FileOutputStream(new File(path+imageName));
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
}
