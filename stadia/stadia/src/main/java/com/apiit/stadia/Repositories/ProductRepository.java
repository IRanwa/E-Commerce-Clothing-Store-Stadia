package com.apiit.stadia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apiit.stadia.ModelClasses.MainSubCategory;
import com.apiit.stadia.ModelClasses.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByMainSubCategory(MainSubCategory mainSubCategory);
	
	List<Product> findByMainSubCategoryOrderByCreatedDateDesc(MainSubCategory mainSubCategory);
}
