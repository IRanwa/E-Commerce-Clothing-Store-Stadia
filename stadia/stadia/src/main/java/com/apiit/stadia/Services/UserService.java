package com.apiit.stadia.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apiit.stadia.DTOClasses.AddressDTO;
import com.apiit.stadia.DTOClasses.LoginDTO;
import com.apiit.stadia.DTOClasses.UserDTO;
import com.apiit.stadia.EnumClasses.AddressType;
import com.apiit.stadia.EnumClasses.UserRole;
import com.apiit.stadia.ModelClasses.Address;
import com.apiit.stadia.ModelClasses.Login;
import com.apiit.stadia.ModelClasses.User;
import com.apiit.stadia.Repositories.AddressRepository;
import com.apiit.stadia.Repositories.LoginRepository;
import com.apiit.stadia.Repositories.UserRepository;

import javax.xml.ws.Response;

@Service
public class UserService {
	
	@Autowired
	LoginRepository loginRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AddressRepository addressRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private ModelClassToDTO modelClassToDTO;

	public ResponseEntity<Boolean> registerUser(User newUser) {
		Optional<Login> loginOptional = loginRepo.findById(newUser.getEmail());
		if(!loginOptional.isPresent()) {
			Login login = newUser.getLogin();
			login.setPass(bcryptEncoder.encode(login.getPass()));
			login.setLastLogin(new Date());
			login = loginRepo.save(login);

			if (login != null) {
				newUser.setLogin(login);
				User user = userRepo.save(newUser);

				if(newUser.getAddress().get(0).getAddress()!=""){
					Address address = newUser.getAddress().get(0);
					address.setUser(user);
					addressRepo.save(address);
				}

				return new ResponseEntity<>(true, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public boolean loginUser(Login login) {
		if(loginRepo.existsByEmailAndPass(login.getEmail(),login.getPass())) {
			return true;
		}
		return false;
	}
	
	public ResponseEntity<UserDTO> getUser(String id) {
		Optional<User> userOptinal = userRepo.findById(id);
		if(userOptinal.isPresent()) {
            User user = userOptinal.get();
            return new ResponseEntity<>(modelClassToDTO.userToDTO(user),HttpStatus.OK);
        }
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
	
	public ResponseEntity<Boolean> deleteUser(String id) {
		try{
			loginRepo.deleteById(id);
			return new ResponseEntity<>(true,HttpStatus.OK);
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return new ResponseEntity<>(false,HttpStatus.OK);
	}
	
	public ResponseEntity<User> updateUser(String id,User newUser) {
		Optional<User> userOptional = userRepo.findById(id);
		Optional<Login> loginOptional = loginRepo.findById(id);
		if(userOptional.isPresent() && loginOptional.isPresent()){
			Login login = loginOptional.get();
			Login newLogin = newUser.getLogin();

			login.setFName(newLogin.getFName());
			login.setLName(newLogin.getLName());
			login = loginRepo.save(login);

			newUser.setLogin(login);
			newUser = userRepo.save(newUser);
			return new ResponseEntity<>(newUser,HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.OK);
	}

	public ResponseEntity<LoginDTO> checkUserRegistered(String email) {
		Optional<Login> loginOptional = loginRepo.findById(email);
		if(loginOptional.isPresent()){
			return new ResponseEntity<>(modelClassToDTO.loginToDTO(loginOptional.get()),HttpStatus.OK);
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
	}
}
