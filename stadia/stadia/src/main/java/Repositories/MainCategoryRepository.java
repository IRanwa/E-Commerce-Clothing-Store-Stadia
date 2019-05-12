package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.MainCategory;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Integer> {

}
