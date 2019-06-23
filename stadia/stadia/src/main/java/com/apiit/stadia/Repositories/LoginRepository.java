package com.apiit.stadia.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apiit.stadia.ModelClasses.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {

	public boolean existsByEmailAndPass(String email,String pass);
	
	public boolean deleteByEmail(String email);
}
