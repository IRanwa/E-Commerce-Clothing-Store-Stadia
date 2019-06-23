package com.apiit.stadia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiit.stadia.ModelClasses.Product;
import com.apiit.stadia.ModelClasses.ProductImages;

public interface ProductImagesRepository extends JpaRepository<ProductImages, Long>{

	public ProductImages findByProductAndPath(Product product, String path);
}
