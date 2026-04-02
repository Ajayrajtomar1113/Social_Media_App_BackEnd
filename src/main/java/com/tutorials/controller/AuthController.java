package com.tutorials.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorials.config.JwtProvider;
import com.tutorials.models.User;
import com.tutorials.repository.UserRepository;
import com.tutorials.request.LoginRequest;
import com.tutorials.response.AuthResponse;
import com.tutorials.service.CustomUserDetailsService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserDetailsService customUserDetails;


	@PostMapping("/signup")
	public AuthResponse createUser(@RequestBody User user) throws Exception {
		
		User isExist = repo.findByEmail(user.getEmail());
		if(isExist != null) {
			throw new Exception("Email already registered");
		}
		
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setGender(user.getGender());
		User savedUser = repo.save(newUser);
		System.out.println("RAW = " + user.getPassword());
		System.out.println("ENC = " + passwordEncoder.encode(user.getPassword()));

		Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
//		Authentication auth =
//			    authenticationManager.authenticate(authentication);

	    String token = JwtProvider.generateToken(authentication);
		
	    AuthResponse res = new AuthResponse(token,"Register Success");
		return res; 
	}
	
	@PostMapping("/signin")
	public AuthResponse signin(@RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticate(loginRequest.getEmail(),loginRequest.getPassword());
		
		String token = JwtProvider.generateToken(authentication);
		
	    AuthResponse res = new AuthResponse(token,"login Success");
		return res; 
	}
	
	private Authentication authenticate(String email, String password) {
		
		UserDetails userDetails = customUserDetails.loadUserByUsername(email);
		
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid username");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
}
