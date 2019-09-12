package com.apiit.stadia.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.apiit.stadia.DTOClasses.AddressDTO;
import com.apiit.stadia.DTOClasses.LoginDTO;
import com.apiit.stadia.ModelClasses.Address;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apiit.stadia.DTOClasses.UserDTO;
import com.apiit.stadia.EnumClasses.UserRole;
import com.apiit.stadia.ModelClasses.Login;
import com.apiit.stadia.ModelClasses.User;
import com.apiit.stadia.Services.UserService;

import javax.xml.ws.Response;

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

	//Address details
	@GetMapping("/GetAddressList/{id}")
	public ResponseEntity<List<AddressDTO>> getAddressList(@PathVariable String id){
		return userService.getAddressList(id);
	}

	@PostMapping("/NewAddress")
	public ResponseEntity<AddressDTO> newAddress(@RequestBody Address address){
		return userService.newAddress(address);
	}

	@PutMapping("/UpdateAddress")
	public ResponseEntity<AddressDTO> updateAddress(@RequestBody Address address){
		return userService.updateAddress(address);
	}

	@DeleteMapping("/DeleteAddress/{id}")
	public ResponseEntity<Boolean> deleteAddress(@PathVariable long id){
		return userService.deleteAddress(id);
	}


	@PostMapping("/SaveAddressList")
	public void saveAddress(@RequestBody  Map<String, Object> data){
		try {
			JSONObject obj = new JSONObject(data);
			BufferedWriter wr = new BufferedWriter(new FileWriter(new File("countries.json")));
			wr.write(obj.toString());
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
