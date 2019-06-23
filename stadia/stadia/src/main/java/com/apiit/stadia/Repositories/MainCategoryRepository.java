package com.apiit.stadia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiit.stadia.EnumClasses.Gender;
import com.apiit.stadia.ModelClasses.MainCategory;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Integer> {
	List<MainCategory> findByType(Gender type);
}
