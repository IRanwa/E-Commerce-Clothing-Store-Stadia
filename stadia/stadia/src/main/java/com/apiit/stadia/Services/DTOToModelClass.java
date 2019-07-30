package com.apiit.stadia.Services;

import com.apiit.stadia.DTOClasses.ProductImagesDTO;
import com.apiit.stadia.ModelClasses.ProductImages;
import org.springframework.stereotype.Service;

import com.apiit.stadia.DTOClasses.SubCategoryDTO;
import com.apiit.stadia.ModelClasses.SubCategory;

@Service
public class DTOToModelClass {
//	public SubCategory dtoToSubCategory(SubCategoryDTO subCategoryDTO) {
//		SubCategory subcat = new SubCategory();
//		subcat.setId(subCategoryDTO.getId());
//		subcat.setSubCatTitle(subCategoryDTO.getSubCatTitle());
//		subcat.setSubCatDesc(subCategoryDTO.getSubCatDesc());
//		return subcat;
//	}

    public ProductImages dtoToProductImages(ProductImagesDTO productImagesDTO){
        ProductImages prodImages = new ProductImages();
        prodImages.setId(productImagesDTO.getId());
        prodImages.setPath(productImagesDTO.getPath());
        return prodImages;
    }
}
