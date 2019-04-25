package Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
	
}
