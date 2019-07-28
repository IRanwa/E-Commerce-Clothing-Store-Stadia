package com.apiit.stadia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiit.stadia.ModelClasses.Sizes;

import java.util.List;

@Repository
public interface SizesRepository extends JpaRepository<Sizes, Long> {

	List<Sizes> findBySize(String size);
}
