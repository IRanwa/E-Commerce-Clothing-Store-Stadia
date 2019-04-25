package Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.MainSubCategory;
import ModelClasses.MainSubCategoryIdentity;

@Repository
public interface MainSubCategoryRepository extends CrudRepository<MainSubCategory, MainSubCategoryIdentity> {

}
