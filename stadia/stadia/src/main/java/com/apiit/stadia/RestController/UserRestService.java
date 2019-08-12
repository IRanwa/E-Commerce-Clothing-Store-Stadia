package com.apiit.stadia.RestController;

import java.util.Date;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apiit.stadia.DTOClasses.UserDTO;
import com.apiit.stadia.EnumClasses.UserRole;
import com.apiit.stadia.ModelClasses.Login;
import com.apiit.stadia.ModelClasses.User;
import com.apiit.stadia.Services.UserService;

@RestController
@CrossOrigin("*")
public class UserRestService {
	
	@Autowired
	UserService userService;

	@PostMapping(value="/Register")
	public ResponseEntity<Boolean> RegisterUser(@RequestBody User user) {
		return userService.registerUser(user);
	}
	
	@GetMapping(value="/Login")
	public boolean LoginUser() {
		Login login = new Login("imeshranwa2","123");
		return userService.loginUser(login);
	}
	
	@GetMapping(value="/User/{id}")
	public UserDTO GetUserDetails(@PathVariable String id) {
		return userService.getUser(id);
	}
	
	@DeleteMapping(value="/User/{id}")
	public boolean DeleteUser(@PathVariable String id) {
		return userService.deleteUser(id);
	}
	
	@PutMapping(value="/User/{id}")
	public User UpdateUser(@PathVariable String id) {
		
		return userService.updateUser(id);
	}
	
	
}
