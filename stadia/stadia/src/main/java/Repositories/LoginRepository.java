package Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ModelClasses.Login;

@Repository
public interface LoginRepository extends CrudRepository<Login, String> {

	public boolean existsByEmailAndPass(String email,String pass);
}
