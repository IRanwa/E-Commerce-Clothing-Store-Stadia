package com.apiit.stadia.RestController;

import com.apiit.stadia.DTOClasses.LoginDTO;
import com.apiit.stadia.ModelClasses.Login;
import com.apiit.stadia.Repositories.LoginRepository;
import com.apiit.stadia.config.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private LoginRepository loginRepo;


	@PostMapping("/authenticate/")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPass());

		final UserDetails userDetails = jwtUserDetailsService
				.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		Optional<Login> loginOptional = loginRepo.findById(authenticationRequest.getEmail());
		if(loginOptional.isPresent()){
			Login login = loginOptional.get();
			return ResponseEntity.ok(new LoginDTO(token,login.getFName(),login.getLName()));
		}else{
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/authenticate/SocialMedia")
	public ResponseEntity<?> createAuthenticationTokenUsingSocialMedia(@RequestBody LoginDTO authenticationRequest)
			throws Exception {

		Optional<Login> loginOptional = loginRepo.findById(authenticationRequest.getEmail());
		if(loginOptional.isPresent()) {
			Login login = loginOptional.get();
			//authenticate(authenticationRequest.getEmail(), loginOptional.get().getPass());

			final UserDetails userDetails = jwtUserDetailsService
					.loadUserByUsername(authenticationRequest.getEmail());

			final String token = jwtTokenUtil.generateToken(userDetails);

			return new ResponseEntity<>(new LoginDTO(token,login.getFName(),login.getLName()), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@PostMapping("/validateToken")
	public ResponseEntity<Boolean> getTokenExpirationDate(@RequestBody LoginDTO loginDTO){
		final UserDetails userDetails = jwtUserDetailsService
				.loadUserByUsername(loginDTO.getEmail());
		boolean status = jwtTokenUtil.validateToken(loginDTO.getJwttoken(), userDetails);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}

	@PostMapping("/getTokenIssuedDate")
	public String getTokenIssuedDate(@RequestBody LoginDTO loginDTO){
		Date date = jwtTokenUtil.getIssuedAtDateFromToken(loginDTO.getJwttoken());
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aaa");
		return dateFormat.format(date);
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
