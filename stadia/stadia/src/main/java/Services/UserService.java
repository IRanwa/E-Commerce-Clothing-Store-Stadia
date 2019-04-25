package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EnumClasses.UserRole;
import ModelClasses.Login;
import ModelClasses.User;
import Repositories.LoginRepository;
import Repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	LoginRepository loginRepo;
	
	@Autowired
	UserRepository userRepo;

	public boolean RegisterUser(Login login) {
		if(!loginRepo.existsById(login.getEmail())) {
			loginRepo.save(login);
			if(login.getRole().equals(UserRole.Consumer)) {
				userRepo.save(new User(login,login.getEmail()));
			}
			return true;
		}
		return false;
	}
	
	public boolean LoginUser(Login login) {
		if(loginRepo.existsByEmailAndPass(login.getEmail(),login.getPass())) {
			return true;
		}
		return false;
	}
}
