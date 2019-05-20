package Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import EnumClasses.Gender;
import ModelClasses.MainCategory;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Integer> {
	List<MainCategory> findByType(Gender type);
}
