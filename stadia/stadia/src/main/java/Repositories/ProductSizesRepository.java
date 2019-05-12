package Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.ProductSizes;
import ModelClasses.ProductSizesIdentity;

@Repository
public interface ProductSizesRepository extends JpaRepository<ProductSizes, ProductSizesIdentity> {

}
