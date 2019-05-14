package Services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import DTOClasses.AddressDTO;
import DTOClasses.LoginDTO;
import DTOClasses.UserDTO;
import EnumClasses.AddressType;
import EnumClasses.UserRole;
import ModelClasses.Address;
import ModelClasses.Login;
import ModelClasses.User;
import Repositories.AddressRepository;
import Repositories.LoginRepository;
import Repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	LoginRepository loginRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	AddressRepository addressRepo;

	public boolean registerUser(User user) {
		if(!loginRepo.existsById(user.getEmail())) {
			loginRepo.save(user.getLogin());
			if(user.getLogin().getRole()==UserRole.Consumer) {
				userRepo.save(user);
				System.out.println("Saved "+user.getAddress());
				if(user.getAddress()!=null && user.getAddress().size()>0) {
					Address address = user.getAddress().get(0);
					address.setUser(user);
					address.setAddType(AddressType.Shipping);
					
					/*
					 * System.out.println(address.getAddress());
					 * System.out.println(address.getCity());
					 * System.out.println(address.getContactNo());
					 * System.out.println(address.getCountry());
					 * System.out.println(address.getFName()); System.out.println(address.getId());
					 * System.out.println(address.getLName());
					 * System.out.println(address.getProvince());
					 * System.out.println(address.getZipCode());
					 * System.out.println(address.getAddType());
					 */
					//addressRepo.save(user.getAddress().get(0));
					
					addressRepo.save(address);
				}
			}
			return true;
		}
		return false;
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
