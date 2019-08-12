package com.apiit.stadia.Services;

import java.util.ArrayList;
import java.util.Date;
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

	public ResponseEntity<Boolean> registerUser(User user) {
		Login login = user.getLogin();
		login.setPass(bcryptEncoder.encode(login.getPass()));
		login.setLastLogin(new Date());
		login = loginRepo.save(login);

		if(login!=null) {
			user.setLogin(login);
			userRepo.save(user);
			return new ResponseEntity<>(true, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public boolean loginUser(Login login) {
		if(loginRepo.existsByEmailAndPass(login.getEmail(),login.getPass())) {
			return true;
		}
		return false;
	}
	
	public UserDTO getUser(String id) {
		User user = userRepo.findById(id).get();
		
		UserDTO userDTO = new UserDTO();
		
		ArrayList<AddressDTO> addressList = new ArrayList<AddressDTO>();
		for(Address address : user.getAddress()) {
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setAddress(address.getAddress());
			addressDTO.setAddType(address.getAddType());
			addressDTO.setCity(address.getCity());
			addressDTO.setContactNo(address.getContactNo());
			addressDTO.setCountry(address.getCountry());
			addressDTO.setFName(address.getFName());
			addressDTO.setId(address.getId());
			addressDTO.setLName(address.getLName());
			addressDTO.setProvince(address.getProvince());
			addressDTO.setZipCode(address.getZipCode());
			addressList.add(addressDTO);
		}
		userDTO.setAddress(addressList);
		userDTO.setContactNo(user.getContactNo());
		userDTO.setDob(user.getDob());
		userDTO.setGender(user.getGender());
		userDTO.setLogin(user.getLogin());
		return userDTO;
	}
	
	public boolean deleteUser(String id) {
		try{
			loginRepo.deleteById(id);
			return true;
		}catch(EmptyResultDataAccessException erda_ex) {
			
		}
		return false;
	}
	
	public User updateUser(String id) {
		User user = new User();
		Login login = loginRepo.findById(id).get();
		login.setFName("I");
		login.setLName("r");
		user.setLogin(login);
		user.setEmail(login.getEmail());
		return userRepo.save(user);
	}
}
