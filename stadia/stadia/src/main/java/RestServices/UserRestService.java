package RestServices;

import java.util.Date;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
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

import DTOClasses.UserDTO;
import EnumClasses.UserRole;
import ModelClasses.Login;
import ModelClasses.User;
import Services.UserService;

@RestController
public class UserRestService {
	
	@Autowired
	UserService userService;
	
	//@CrossOrigin("*")
	@PostMapping(value="/Register")
	//public String RegisterUser(@RequestBody User user) {
	//user.getLogin().setLastLogin(new Date());
	//user.getLogin().setRole(UserRole.Consumer);
	public String RegisterUser() {
		Login login = new Login("imeshranwa2","123","Imesh","Ranawaka",UserRole.Consumer,new Date());
		User user = new User(login,"imeshranwa2");
		return "{\"Status\":"+userService.registerUser(user)+"}";
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
