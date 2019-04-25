package Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.SubCategory;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {

}
