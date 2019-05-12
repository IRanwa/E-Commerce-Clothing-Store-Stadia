package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.MainSubCategory;

@Repository
public interface MainSubCategoryRepository extends JpaRepository<MainSubCategory, Long> {

}
