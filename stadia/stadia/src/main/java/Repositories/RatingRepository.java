package Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.Rating;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

}
