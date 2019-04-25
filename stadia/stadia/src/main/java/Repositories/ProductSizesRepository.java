package Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.ProductSizes;
import ModelClasses.ProductSizesIdentity;

@Repository
public interface ProductSizesRepository extends CrudRepository<ProductSizes, ProductSizesIdentity> {

}
