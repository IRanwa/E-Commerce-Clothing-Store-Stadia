package com.apiit.stadia.RestController;

import java.util.Date;

import com.apiit.stadia.DTOClasses.LoginDTO;
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

	@PostMapping("/Register")
	public ResponseEntity<Boolean> RegisterUser(@RequestBody User user) {
		return userService.registerUser(user);
	}
	
	@PostMapping("/GetUser")
	public ResponseEntity<UserDTO> GetUserDetails(@RequestBody User user) {
		return userService.getUser(user.getEmail());
	}

	@PostMapping("/CheckUser")
	public ResponseEntity<LoginDTO> CheckUserRegistered(@RequestBody User user) {
		return userService.checkUserRegistered(user.getEmail());
	}
	
	@DeleteMapping("/DeleteUser/{id}")
	public ResponseEntity<Boolean> DeleteUser(@PathVariable String id) {
		return userService.deleteUser(id);
	}
	
	@PutMapping("/UpdateUser/{id}")
	public ResponseEntity<User> UpdateUser(@PathVariable String id, @RequestBody User user) {
		return userService.updateUser(id,user);
	}
	
	
}
