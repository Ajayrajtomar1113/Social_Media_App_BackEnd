package com.tutorials.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorials.exceptions.UserException;
import com.tutorials.models.User;
import com.tutorials.repository.UserRepository;
import com.tutorials.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/api/users/id/{userId}")
	public User getUserById(@PathVariable Integer userId) throws UserException{
		
		User user = userService.findById(userId);
		return user;
	}
	
	@GetMapping("/api/users/email/{userEmail}")
	public User getUserByEmail(@PathVariable String userEmail) throws UserException{
		
		User user = userService.findUserByEmail(userEmail);
		return user;
	}
	
	@GetMapping("/api/users")
	public List<User> getAllUsers(){
		return userService.findAllUser();
	}
	
	@PutMapping("/api/user/update")
	public User updateUser(@RequestBody User user,@RequestHeader("Authorization") String jwt) {
		User reqUser = userService.findUserByJwt(jwt);
		
		User newUser = userService.updateUser(user, reqUser.getId());
		return newUser;
	}
	
	@PutMapping("/api/users/follow/{userId2}")
	public User followUserHandler(@RequestHeader("Authorization") String jwt,@PathVariable Integer userId2) throws UserException {
		
		User reqUser = userService.findUserByJwt(jwt);
		User user = userService.followUser(reqUser.getId(), userId2);
		return user;
	}
	

	@GetMapping("/api/users/search")
	public List<User> searchUser(@RequestParam("query") String query){
		
		List<User> users = userService.searchUser(query);
		return users;
	}
	
	@GetMapping("/api/users/profile")
	public User getUserFromToken(@RequestHeader("Authorization") String jwt) {
		User user = userService.findUserByJwt(jwt);
		return user;
	}
	
}
