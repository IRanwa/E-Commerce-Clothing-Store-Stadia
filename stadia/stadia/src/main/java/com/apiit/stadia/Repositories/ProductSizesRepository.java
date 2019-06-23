package com.apiit.stadia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiit.stadia.ModelClasses.Product;
import com.apiit.stadia.ModelClasses.ProductSizes;
import com.apiit.stadia.ModelClasses.Sizes;

@Repository
public interface ProductSizesRepository extends JpaRepository<ProductSizes, Long> {

	public ProductSizes findByProductAndSizes(Product product, Sizes sizes);
}
