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

import com.apiit.stadia.DTOClasses.MainCategoryDTO;
import com.apiit.stadia.DTOClasses.SubCategoryDTO;
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
	
	private final int PAGE_COUNT = 2;

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
		Pageable pages = PageRequest.of(pageNo, PAGE_COUNT);
		
		Page<MainCategory> mainCatList = mainCatRepo.findAll(pages);
		List<MainCategoryDTO> mainCatDTOList = new ArrayList<>();
		for(MainCategory mainCat : mainCatList) {
			mainCatDTOList.add(modelToDTO.mainCategoryToDTO(mainCat));
		}
		return mainCatDTOList;
	}
	
	public double getMainCatPages() {
		return Math.ceil(Double.valueOf(mainCatRepo.count())/PAGE_COUNT);
	}

	public ResponseEntity<MainCategoryDTO> getMainCategory(int id){
		Optional<MainCategory> mainCatOptinal = mainCatRepo.findById(id);
		if(mainCatOptinal.isPresent()){
			return new ResponseEntity<>(modelToDTO.mainCategoryToDTO(mainCatOptinal.get()),HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	public MainCategoryDTO addMainCategory(MainCategory mainCategory) {
		Optional<MainCategory> mainCatOptional = mainCatRepo.findById(mainCategory.getId());
		SubCategory subcat = null;
		
		if(!mainCatOptional.isPresent()) {

			String imageName = saveImage("/Images/MainCategory/",mainCategory.getMainCatTitle(),mainCategory.getMainCatImg());
			if(imageName!=null) {
				mainCategory.setMainCatImg(imageName);
				subcat = mainCategory.getSubCategory().get(0);
				mainCategory.setSubCategory(new ArrayList<>());
			}
		}else {
			subcat = mainCategory.getSubCategory().get(0);
			mainCategory = mainCatOptional.get();
		}

		Optional<SubCategory> subCatOptional = subCatRepo.findById(subcat.getId());
		if(!subCatOptional.isPresent()){
			String subCatImage = saveImage("/Images/SubCategory/",subcat.getSubCatTitle(),subcat.getSubCatImg());
			subcat.setSubCatImg(subCatImage);
			System.err.println(mainCategory.getSubCategory().size());

			subcat = subCatRepo.save(subcat);
		}

		if (mainCategory != null) {
			mainCategory = mainCatRepo.save(mainCategory);

			MainSubCategory mainSubCatList = mainSubCatRepo.findByMainCategoryAndSubCategory(mainCategory, subcat);
			if(mainSubCatList!=null) {
				MainSubCategory mainSubCat = new MainSubCategory();
				mainSubCat.setMainCategory(mainCategory);
				mainSubCat.setSubCategory(subcat);
				mainSubCatRepo.save(mainSubCat);
			}
			return modelToDTO.mainCategoryToDTO(mainCategory);
		}
		return null;
	}
	
	public ResponseEntity<Boolean> deleteMainCategory(Integer id) {
		try {
			Optional<MainCategory> mainCatOptional = mainCatRepo.findById(id);
			if(mainCatOptional.isPresent()){
				MainCategory mainCat = mainCatOptional.get();
				boolean deleteStatus = deleteImage("/Images/MainCategory/"+mainCat.getMainCatImg());
				if(deleteStatus){
					ArrayList<Integer> subIds = new ArrayList<>();
					for(SubCategory subCat : mainCat.getSubCategory()){
						if(subCat.getMainCategory().size()==1){
							subIds.add(subCat.getId());
						}
					}
					mainCatRepo.deleteById(id);
					if(subIds.size()>0) {
						for(int subId : subIds){
							Optional<SubCategory> subCatOptional = subCatRepo.findById(subId);
							if(subCatOptional.isPresent()){
								boolean status = deleteImage("/Images/SubCategory/"+subCatOptional.get().getSubCatImg());
								if(status){
									subCatRepo.deleteById(subId);
								}
							}
						}
					}
					return new ResponseEntity<>(true,HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<MainCategoryDTO> updateCategory(MainCategory newMainCat, int id) {
		Optional<MainCategory> mainCatOptional = mainCatRepo.findById(id);
		if(mainCatOptional.isPresent()){
			MainCategory mainCat = mainCatOptional.get();

			System.err.println("new main "+newMainCat.getMainCatTitle());
			if(newMainCat.getMainCatTitle()!=null &&  !newMainCat.getMainCatTitle().equalsIgnoreCase("")){
				mainCat.setMainCatTitle(newMainCat.getMainCatTitle());
			}
			if(newMainCat.getMainCatDesc()!=null && !newMainCat.getMainCatDesc().equalsIgnoreCase("")){
				mainCat.setMainCatDesc(newMainCat.getMainCatDesc());
			}
			if(newMainCat.getType()!=null && !newMainCat.getType().toString().equalsIgnoreCase("")) {
				mainCat.setType(newMainCat.getType());
			}

			if(newMainCat.getMainCatImg()!=null && !newMainCat.getMainCatImg().equalsIgnoreCase("")) {
				boolean status = deleteImage("/Images/MainCategory/"+mainCat.getMainCatImg());
				if(status) {
					String imageName = saveImage("/Images/MainCategory/", newMainCat.getMainCatTitle(), newMainCat.getMainCatImg());
					if (imageName != null) {
						mainCat.setMainCatImg(imageName);
					} else {
						return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}else{
					return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			mainCat = mainCatRepo.save(mainCat);
			return new ResponseEntity<>(modelToDTO.mainCategoryToDTO(mainCat), HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	//Sub Category
	public double getSubCatPages() {
		return Math.ceil(Double.valueOf(subCatRepo.count())/PAGE_COUNT);
	}

	public List<SubCategoryDTO> getSubCategoryList(int pageNo){

		List<SubCategoryDTO> newSubCatList = new ArrayList<>();
		if(pageNo>=0) {
			Pageable pages = PageRequest.of(pageNo, PAGE_COUNT);

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

	public ResponseEntity<SubCategoryDTO> getSubCategory(int id){
		Optional<SubCategory> subCatOptinal = subCatRepo.findById(id);
		if(subCatOptinal.isPresent()){
			return new ResponseEntity<>(modelToDTO.subCategoryToDTO(subCatOptinal.get()),HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}

	public List<SubCategoryDTO> getMainSubCategory(int id) {
		List<SubCategoryDTO> subCatDTOList = new ArrayList<>();
		Optional<MainCategory> mainCatOptional = mainCatRepo.findById(id);
		if(mainCatOptional.isPresent()){
			List<SubCategory> subcatList = mainCatOptional.get().getSubCategory();
			for(SubCategory subCat : subcatList){
				subCatDTOList.add(modelToDTO.subCategoryToDTO(subCat));
			}
		}
		return subCatDTOList;
	}

	public SubCategory addSubCategory(SubCategory subCat,int mainCatId) {
		String imageName = saveImage("/Images/SubCategory/",subCat.getSubCatTitle(),subCat.getSubCatImg());
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
	
	public ResponseEntity<Boolean> deleteSubCategory(int id) {
		try {
			Optional<SubCategory> subCatOptional = subCatRepo.findById(id);
			if(subCatOptional.isPresent()){
				SubCategory subCat = subCatOptional.get();
				boolean deleteStatus = deleteImage("/Images/SubCategory/"+subCat.getSubCatImg());
				if(deleteStatus){
					ArrayList<Integer> mainCatIds = new ArrayList<>();
					for(MainCategory mainCat : subCat.getMainCategory()){
						if(mainCat.getSubCategory().size()==1){
							mainCatIds.add(mainCat.getId());
						}
					}
					subCatRepo.deleteById(id);
					if(mainCatIds.size()>0){
						for(int mainCatId : mainCatIds){
							Optional<MainCategory> mainCatOptional = mainCatRepo.findById(mainCatId);
							if(mainCatOptional.isPresent()){
								boolean status = deleteImage("/Images/MainCategory/"+mainCatOptional.get().getMainCatImg());
								if(status){
									mainCatRepo.deleteById(mainCatId);
								}
							}
						}
					}
					return new ResponseEntity<>(true,HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}

		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public ResponseEntity<SubCategoryDTO> updateSubCategory(SubCategory newSubCat, int id) {
		Optional<SubCategory> subCatOptional = subCatRepo.findById(id);
		if(subCatOptional.isPresent()){
			SubCategory subCat = subCatOptional.get();

			if(newSubCat.getSubCatTitle()!=null &&  !newSubCat.getSubCatTitle().equalsIgnoreCase("")){
				subCat.setSubCatTitle(newSubCat.getSubCatTitle());
			}
			if(newSubCat.getSubCatDesc()!=null && !newSubCat.getSubCatDesc().equalsIgnoreCase("")){
				subCat.setSubCatDesc(newSubCat.getSubCatDesc());
			}

			if(newSubCat.getSubCatImg()!=null && !newSubCat.getSubCatImg().equalsIgnoreCase("")) {
				boolean status = deleteImage("/Images/SubCategory/"+subCat.getSubCatImg());
				if(status) {
					String imageName = saveImage("/Images/SubCategory/", newSubCat.getSubCatTitle(), newSubCat.getSubCatImg());
					if (imageName != null) {
						subCat.setSubCatImg(imageName);
					} else {
						return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}else{
					return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			subCat = subCatRepo.save(subCat);
			return new ResponseEntity<>(modelToDTO.subCategoryToDTO(subCat), HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
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

	public boolean deleteImage(String imagePath){
		String path = System.getProperty("user.dir") + imagePath;
		File file = new File(path);
		if(file.exists()) {
			if(file.delete()) {
				System.err.println(path);
				return true;
			}
			return false;
		}else{
			return true;
		}
	}
}
