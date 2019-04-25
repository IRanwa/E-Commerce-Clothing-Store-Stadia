package RestServices;

import java.util.Date;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import EnumClasses.UserRole;
import ModelClasses.Address;
import ModelClasses.Login;
import ModelClasses.User;
import Services.UserService;

@RestController
public class UserRestService {
	
	@Autowired
	UserService userService;
	
	@CrossOrigin("*")
	@PostMapping(value="/Register")
	public User RegisterUser(@RequestBody User user) {
		System.out.println("Display User");
		System.out.println(user.getEmail());
		System.out.println(user.getLogin().getFName());
		System.out.println(user.getLogin().getLName());
		System.out.println(user.getLogin().getPass());
		System.out.println(user.getLogin().getLastLogin());
		System.out.println(user.getLogin().getRole());
		
		user.getLogin().setLastLogin(new Date());
		user.getLogin().setRole(UserRole.Consumer);
		
		System.out.println("Display Address"); System.out.println(user.getAddress());
		System.out.println(user.getAddress().get(0).getAddress());
		System.out.println(user.getAddress().get(0).getCity());
		System.out.println(user.getAddress().get(0).getFName());
	  
		return user;
	}
	
	@RequestMapping(value="/Login",method=RequestMethod.GET)
	public boolean LoginUser() {
		Login login = new Login("imesh@gmail.com","1234");
		return userService.LoginUser(login);
	}
}
