package com.apiit.stadia.Repositories;

import java.util.List;

import com.apiit.stadia.ModelClasses.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiit.stadia.ModelClasses.MainCategory;
import com.apiit.stadia.ModelClasses.MainSubCategory;

@Repository
public interface MainSubCategoryRepository extends JpaRepository<MainSubCategory, Long> {
	List<MainSubCategory> findByMainCategoryAndSubCategory(MainCategory mainCategory, SubCategory subCategory);
}
