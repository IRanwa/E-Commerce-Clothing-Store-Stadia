package com.apiit.stadia;

import java.util.ArrayList;
import java.util.Optional;

import com.apiit.stadia.ModelClasses.Login;
import com.apiit.stadia.Repositories.LoginRepository;
import com.apiit.stadia.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    LoginRepository loginRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Login> loginOptional = loginRepo.findById(username);
        if(loginOptional.isPresent()){
            Login login = loginOptional.get();
            return new User(login.getEmail(),login.getPass(),new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found with username: " + username);

//        if ("javainuse".equals(username)) {
//            return new User("javainuse", "$2a$10$SZDUlTyvdAk0hP0lL5yMhe1z8Nfc.iQciEt0AEl04FtgJHw6zZTUC",
//                    new ArrayList<>());
//        } else {
//
//        }


    }
}
