package Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.MainCategory;

@Repository
public interface MainCategoryRepository extends CrudRepository<MainCategory, Integer> {

}
