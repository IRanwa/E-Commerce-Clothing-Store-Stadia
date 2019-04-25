package Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.Sizes;

@Repository
public interface SizesRepository extends CrudRepository<Sizes, Long> {

}
